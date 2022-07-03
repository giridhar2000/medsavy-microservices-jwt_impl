package com.medsavy.medsavyinventorymanagementservice.services;

import static com.medsavy.medsavyinventorymanagementservice.enums.Transaction.SELL;
import static com.medsavy.medsavyinventorymanagementservice.utils.InventoryConstants.INVENTORY_CREATED;
import static com.medsavy.medsavyinventorymanagementservice.utils.InventoryConstants.INVENTORY_CREATION_FAILURE;
import static com.medsavy.medsavyinventorymanagementservice.utils.InventoryConstants.MEDICINE_ADD_FAILURE;
import static com.medsavy.medsavyinventorymanagementservice.utils.InventoryConstants.MEDICINE_ADD_SUCCESS;

import antlr.StringUtils;
import com.medsavy.medsavyinventorymanagementservice.entity.MedIVEntity;
import com.medsavy.medsavyinventorymanagementservice.entity.SalesEntity;
import com.medsavy.medsavyinventorymanagementservice.entity.UserEntity;
import com.medsavy.medsavyinventorymanagementservice.entity.UserIVEntity;
import com.medsavy.medsavyinventorymanagementservice.enums.Transaction;
import com.medsavy.medsavyinventorymanagementservice.exchanges.AddMedRequest;
import com.medsavy.medsavyinventorymanagementservice.exchanges.AddMedResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.CreateInventoryResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.DeleteMedResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.GetSalesResponse;
import com.medsavy.medsavyinventorymanagementservice.exchanges.IVUpdateRequest;
import com.medsavy.medsavyinventorymanagementservice.exchanges.IVUpdateResponse;
import com.medsavy.medsavyinventorymanagementservice.repository.InventoryRepository;
import com.medsavy.medsavyinventorymanagementservice.repository.SalesRepository;
import com.medsavy.medsavyinventorymanagementservice.repository.UserInventoryRepository;
import com.medsavy.medsavyinventorymanagementservice.repository.UserRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryManagementServiceImpl implements InventoryManagementService{


  private final InventoryRepository inventoryRepository;

  private final UserInventoryRepository userInventoryRepository;

  private final UserRepository userRepository;

  private final ModelMapper modelMapper;

  private final SalesRepository salesRepository;



  @Override
  public AddMedResponse addMedInInventory(AddMedRequest request) {

    AddMedResponse response = new AddMedResponse();
    UserIVEntity userInventoryEntity = userInventoryRepository
        .findUserInventoryEntityByUserId(request.getUserId());
    // FIXME : Debug inventory not found for user even though it exist
    if(userInventoryEntity == null) {
      response.setMessage("Inventory Not Found for userID "+request.getUserId());
      response.setSuccess(false);
      return response;
    }

    // Check medicine with same name , expiry date exist or not
    // If exist than update the batch by increasing qty and setting the recent price
    // else create a new batch with the med


    AddMedResponse addMedResponse = insertOrUpdateMedIVEntity(
        userInventoryEntity.getInventoryId(), request
    );

    return addMedResponse;
  }

  private AddMedResponse insertOrUpdateMedIVEntity(Integer ivID, AddMedRequest request) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String expDate = dateFormat.format(request.getExpiryDate());
    AddMedResponse response = new AddMedResponse();
    MedIVEntity medIVEntity = inventoryRepository
        .findMedInventoryEntityByNameAndAndExpiryDateAndInventoryId(
            request.getName(), expDate, ivID);

    if(medIVEntity != null) {
      medIVEntity.increaseQuantity(request.getQuantity());
      medIVEntity.updatePrice(request.getPrice());
      log.info("inside [insertOrUpdateMedIVEntity] Updating existing medicine batch {} in "
              + "inventory {}", medIVEntity.getBatchId(), ivID);

       inventoryRepository.save(medIVEntity);

       response =  modelMapper.map(medIVEntity, AddMedResponse.class);
       response.setMessage("Medicine batch updated");
       response.setSuccess(true);
       return response;
    }

    // Constructing new medicine batch
    log.info("inside [insertOrUpdateMedIVEntity] Constructing new medicine batch in inventory {}",
        ivID);


    medIVEntity = MedIVEntity.builder()
        .name(request.getName())
        .type(request.getType())
        .price(request.getPrice())
        .expiryDate(expDate)
        .quantity(request.getQuantity())
        .transaction(Transaction.PURCHASE.name())
        .inventoryId(ivID)
        .build();

    inventoryRepository.save(medIVEntity);

    response = modelMapper.map(medIVEntity, AddMedResponse.class);
    response.setMessage("Medicine batch added");
    response.setSuccess(true);

    return response;
  }

  @Override
  public CreateInventoryResponse createInventoryForUser(Integer userId) {

    UserEntity userEntity = null;
    CreateInventoryResponse response = new CreateInventoryResponse();
    UserIVEntity userInventoryEntity = new UserIVEntity();
    Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);

    if(optionalUserEntity.isPresent()) {
      userEntity = optionalUserEntity.get();
      log.info("inside [createInventoryForUser] creating inventory for user {}",
          userEntity.getUsername());

      if(userInventoryRepository.existsByUserEntity(userEntity)) {
        log.info("inside [createInventoryForUser] unable to create inventory for user {}"
            + " inventory already exists", userEntity.getUsername());
        response.setMessage(INVENTORY_CREATION_FAILURE+", Inventory already exists");
        response.setSuccess(false);
        return response;
      }

      userInventoryEntity.setUserEntity(userEntity);
      userInventoryRepository.save(userInventoryEntity);
      response = modelMapper.map(userInventoryEntity, CreateInventoryResponse.class);
      response.setMessage(INVENTORY_CREATED);
      response.setSuccess(true);
      return response;
    }

    if(optionalUserEntity.isEmpty()) {
      log.info("Unable to create inventory. No user found with id {}", userId);
      response.setMessage(INVENTORY_CREATION_FAILURE+", No user found");
      response.setSuccess(false);
    }

    return response;
  }

  @Override
  public IVUpdateResponse updateMedInInventoryAfterSell(Integer inventoryId, IVUpdateRequest updateRequest) {

    IVUpdateResponse updateResponse = new IVUpdateResponse();
    List<MedIVEntity> medIVEntities = inventoryRepository
        .findMedInventoryEntitiesByInventoryIdAndSearchString(
            inventoryId,
            updateRequest.getMedName()
        );

    if(medIVEntities.isEmpty()) {
      updateResponse.setMedName(updateRequest.getMedName());
      updateResponse.setInventoryId(inventoryId);
      updateResponse.setMessage(String.format("%s not found in inventory. Try others",
          updateRequest.getMedName()
      ));
      updateResponse.setSuccess(false);
      return updateResponse;
    }

    medIVEntities.sort(new Comparator<MedIVEntity>() {
      @Override
      public int compare(MedIVEntity o1, MedIVEntity o2) {
        return o2.getQuantity() - o1.getQuantity();
      }
    });

    // update all batches or single batch of a medicine based on qty
    Integer totalQty = getTotalQtyForSingleMedIVEntity(medIVEntities);

    int requiredQty = updateRequest.getQuantity();
    List<MedIVEntity> possibleUpdatedEntities = new ArrayList<>();
    if(requiredQty <= totalQty) {
     possibleUpdatedEntities = updateMedIVEntitiesQuantity(
         medIVEntities, totalQty, requiredQty
     );

     inventoryRepository.saveAll(possibleUpdatedEntities);
     Integer updatedQuantity = totalQty - requiredQty;
     Integer batchesUpdated = possibleUpdatedEntities.size();
     updateResponse = new IVUpdateResponse(updateRequest.getMedName(), inventoryId,
         updatedQuantity, batchesUpdated);
     updateResponse.setMessage("Inventory Updated successfully");
     updateResponse.setSuccess(true);
     Integer sellerId = userInventoryRepository.findById(inventoryId)
         .get()
         .getUserEntity()
         .getId();

     createSalesReport(inventoryId,
         updateRequest.getMedName(),
         updateRequest.getCustomerId(),
         updateRequest.getQuantity(),
         sellerId, requiredQty * medIVEntities.get(0).getPrice()
     );
     return updateResponse;
    }
    updateResponse.setMedName(updateRequest.getMedName());
    updateResponse.setInventoryId(updateResponse.getInventoryId());
    updateResponse.setMessage("Update failed due to insufficient quantity");
    updateResponse.setSuccess(false);

    return updateResponse;
  }

  @Override
  public GetSalesResponse getSalesDataBySalesId(Integer salesId) {
    Optional<SalesEntity> salesEntity = salesRepository.findById(salesId);
    GetSalesResponse salesResponse = new GetSalesResponse();
    if(salesEntity.isPresent()) {
      List<SalesEntity> salesEntityList = new ArrayList<>();
      salesEntityList.add(salesEntity.get());
      salesResponse = new GetSalesResponse(salesEntityList);
    }

    return salesResponse;
  }

  @Override
  public DeleteMedResponse deleteMedByBatchId(Integer batchId, Integer quantity) {
    MedIVEntity medIVEntity = inventoryRepository.findMedInventoryEntityByBatchId(batchId);

    Boolean isDecreased = medIVEntity.decreaseQuantity(quantity);
    DeleteMedResponse response = new DeleteMedResponse();


    if(isDecreased) {
      inventoryRepository.save(medIVEntity);
      response.setBatchId(batchId);
      response.setQuantity(quantity);
      response.setMedName(medIVEntity.getName());
      response.setMessage("Medicines deleted successfully");
      return response;
    }

    response.setMessage("Unable to delete medicines, requested quantity must be smaller than"
        + "available quantity");

    return response;
  }

  private void createSalesReport(Integer inventoryId, String medName, Integer customerId,
      Integer quantity, Integer sellerId, double amount) {

    SalesEntity salesEntity = new SalesEntity(
        SELL.name(),
        medName,
        customerId,
        sellerId,
        quantity,
        amount
    );

    salesRepository.save(salesEntity);
  }

  private List<MedIVEntity> updateMedIVEntitiesQuantity(List<MedIVEntity> medIVEntities, int totalQty,
      int requiredQty) {
    List<MedIVEntity> toBeUpdatedMedIVEntities = new ArrayList<>();

    for (MedIVEntity medIVEntity : medIVEntities) {
      if (requiredQty != 0 && requiredQty <= medIVEntity.getQuantity()) {
        medIVEntity.decreaseQuantity(requiredQty);
        toBeUpdatedMedIVEntities.add(medIVEntity);
        break;

      } else if (requiredQty != 0 && requiredQty > medIVEntity.getQuantity()) {
        int current = medIVEntity.getQuantity();
        medIVEntity.decreaseQuantity(current);
        requiredQty = requiredQty - current;
        toBeUpdatedMedIVEntities.add(medIVEntity);
      }
    }
    return toBeUpdatedMedIVEntities;
  }

  private Integer getTotalQtyForSingleMedIVEntity(List<MedIVEntity> medIVEntities) {
    AtomicReference<Integer> total = new AtomicReference<>(0);
    medIVEntities.forEach(medIVEntity -> total.updateAndGet(v -> v + medIVEntity.getQuantity()));
    return total.get();
  }

}
