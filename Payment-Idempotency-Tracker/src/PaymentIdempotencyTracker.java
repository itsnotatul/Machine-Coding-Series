import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaymentIdempotencyTracker {
    Map<String, MapEntry> idempotencyMap;

    PaymentIdempotencyTracker(){
        idempotencyMap = new HashMap<>();
    }

    public ResultDto process(String idempotencyKey, String  requestPayload){
        if (!isPayloadValid(idempotencyKey, requestPayload)) {
           return new ResultDto(ResultDto.ResultStatus.INVALID, idempotencyKey+ UUID.randomUUID().toString());
        }
        // 1. map to dto
        MapEntry mapEntry = new MapEntry(requestPayload, null);

        // 2. check in map
        if (!existsInIdempotencyMap(idempotencyKey)){
            addEntryToIdempotencyMap(idempotencyKey, mapEntry);
            return idempotencyMap.get(idempotencyKey).getResultDto();
        }
        // 3. if exists -> check for conflict
        if (isConflictPresent(idempotencyKey, mapEntry)){
            return new ResultDto(ResultDto.ResultStatus.CONFLICT, "conflict came -> payload mismatching");
        }

        return idempotencyMap.get(idempotencyKey).getResultDto();
    }

    private boolean isConflictPresent(String idempotencyKey, MapEntry payloadDto) {
       MapEntry mapEntry = idempotencyMap.get(idempotencyKey);
       if(mapEntry.getPayloadString().equals(payloadDto.getPayloadString())){
           return false; // duplicate
       }
       return true;
    }

    private void addEntryToIdempotencyMap(String idempotencyKey, MapEntry mapEntry) {
       ResultDto resultDto =  mapEntry.getResultDto();
       resultDto = new ResultDto(ResultDto.ResultStatus.SUCCESS, getTransactionId(idempotencyKey));
       mapEntry.setResultDto(resultDto);
        idempotencyMap.put(idempotencyKey, mapEntry);
    }

    private String getTransactionId(String idempotencyKey) {
        return idempotencyKey+ UUID.randomUUID();
    }

    private boolean existsInIdempotencyMap(String idempotencyKey) {
        if(!idempotencyMap.containsKey(idempotencyKey)){
            return false;
        }
        return true;
    }

    private boolean isPayloadValid(String idempotencyKey, String requestPayload) {
        if( idempotencyKey == null || idempotencyKey.isBlank() || requestPayload == null || requestPayload.isBlank()){
            System.out.println("Invalid payload");
            return false;
        }
        return true;
    }
}
