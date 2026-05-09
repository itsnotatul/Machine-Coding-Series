public class MapEntry {
    private final String payloadString;
    private ResultDto resultDto;

    public String getPayloadString() {
        return payloadString;
    }

    public ResultDto getResultDto() {
        return resultDto;
    }

    public void setResultDto(ResultDto resultDto) {
        this.resultDto = resultDto;
    }

    public MapEntry(String payloadString, ResultDto resultDto) {
        this.payloadString = payloadString;
        this.resultDto = resultDto;
    }


}
