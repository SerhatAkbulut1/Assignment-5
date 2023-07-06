import java.time.Duration;
import java.time.LocalDateTime;
/**
 * A subclass of SmartDevice that represents a smart camera.
 */
public class SmartCamera extends SmartDevice {
    private long megabaytOfVideo;      //The size of the video in megabytes.
    private LocalDateTime time;        //The date and time when the camera was last used.
    private Duration recordTime;
    private double megabaytsPerRecord;
    private boolean isRecording;
    /**
     * Creates a new SmartCamera with the given name.
     *
     * @param name               the name of the camera
     * @param initialStatus
     * @param megabytesPerRecord
     */
    public SmartCamera(String name, boolean initialStatus, double megabytesPerRecord){
        super(name,initialStatus);
        this.megabaytsPerRecord =megabytesPerRecord;
    }
    /**
     * Creates a new SmartCamera with the given name and maximum video segment size.
     * @param name the name of the camera
     * @param megabaytsPerRecord the size of each recorded video segment in megabytes
     */
    public SmartCamera(String name, double megabaytsPerRecord) {
        super(name);
        this.megabaytsPerRecord =megabaytsPerRecord;
    }
    /**
     * Creates a new SmartCamera with the given name, recording status, and maximum video segment size.
     * @param name the name of the camera
     * @param isOn whether the camera is currently on
     * @param megabaytsPerRecord the size of each recorded video segment in megabytes
     */
    public SmartCamera(String name, boolean isOn, int megabaytsPerRecord) {
        super(name, isOn);
        this.megabaytsPerRecord = megabaytsPerRecord;
        this.isRecording = false;
    }
    /**
     * Returns the maximum length of time that the camera can record.
     * @return the maximum length of time that the camera can record
     */
    public Duration getRecordTime() {
        return recordTime;
    }
    /**
     * Sets the maximum length of time that the camera can record.
     * @param recordTime the maximum length of time that the camera can record
     */
    public void setRecordTime(Duration recordTime) {
        this.recordTime = recordTime;
    }
    /**
     Returns whether or not the camera is currently recording.
     @return true if the camera is recording, false otherwise.
     */
    public boolean isRecording() {
        return isRecording;
    }
    /**
     Sets the recording status of the camera.
     @param recording the recording status to set (true for recording, false for not recording)
     */
    public void setRecording(boolean recording) {
        isRecording = recording;
    }
    /**
     * Returns the date and time when the camera was last used.
     * @return the date and time when the camera was last used
     */
    public LocalDateTime getTime() {
        return time;
    }
    /**
     * Sets the date and time when the camera was last used.
     * @param time the date and time when the camera was last used
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    /**
     * Returns the number of megabytes per record.
     * @return the number of megabytes per record
     */
    public double getMegabaytsPerRecord() {
        return megabaytsPerRecord;
    }
    /**
     * Sets the number of megabytes per record.
     * @param megabaytsPerRecord the number of megabytes per record to set
     */
    public void setMegabaytsPerRecord(int megabaytsPerRecord) {
        this.megabaytsPerRecord = megabaytsPerRecord;
    }
    /**
     * Calculates the size of a video recording in megabytes, based on the duration of the recording and
     * the number of megabytes per record.
     * @param recordTime the duration of the recording
     * @param megabaytsPerRecord the number of megabytes per record
     */
    public void calcMegabaytOfVideo(Duration recordTime,int megabaytsPerRecord){
        this.megabaytOfVideo += (recordTime.getSeconds()/60)*megabaytsPerRecord;
    }

    public long getMegabaytOfVideo() {
        return megabaytOfVideo;
    }

    public void setMegabaytOfVideo(long megabaytOfVideo) {
        this.megabaytOfVideo = megabaytOfVideo;
    }
}