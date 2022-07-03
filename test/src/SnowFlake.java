public class SnowFlake {

    //Data center (machine room) id
    private long datacenterId;
    //Machine ID
    private long workerId;
    //Same time series
    private long sequence;

    public SnowFlake(long workerId, long datacenterId) {
        this(workerId, datacenterId, 0);
    }

    public SnowFlake(long workerId, long datacenterId, long sequence) {
        //Legal judgment
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        System.out.printf("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
            timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    //Start timestamp
    private long twepoch = 1420041600000L;

    //Machine room number, the number of digits occupied by the ID of the machine room is 5 bits, the maximum is 11111 (binary) - > 31 (decimal)
    private long datacenterIdBits = 5L;

    //Machine ID所占的位数 5个bit 最大:11111(2进制)--> 31(10进制)
    private long workerIdBits = 5L;

    //5 bit can only have 31 digits at most, that is, the machine ID can only be within 32 at most
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);

    //5-bit can only have 31 digits at most, and the machine room ID can only be within 32 at most
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    //Same time series所占的位数 12个bit 111111111111 = 4095  最多就是同一毫秒生成4096个
    private long sequenceBits = 12L;

    //Offset of workerid
    private long workerIdShift = sequenceBits;

    //Offset of datacenter ID
    private long datacenterIdShift = sequenceBits + workerIdBits;

    //Offset of timestampleft
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    //Serial number mask 4095 (0b111111 = 0xfff = 4095)
    //It is used for the sum operation of serial number to ensure that the maximum value of serial number is between 0-4095
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    //Last timestamp
    private long lastTimestamp = -1L;


    //Get machine ID
    public long getWorkerId() {
        return workerId;
    }


    //Get machine room ID
    public long getDatacenterId() {
        return datacenterId;
    }


    //Get the latest timestamp
    public long getLastTimestamp() {
        return lastTimestamp;
    }


    //Get the next random ID
    public synchronized long nextId() {
        //Gets the current timestamp in milliseconds
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                lastTimestamp - timestamp));
        }

        //Weight removal
        if (lastTimestamp == timestamp) {

            sequence = (sequence + 1) & sequenceMask;

            //Sequence sequence is greater than 4095
            if (sequence == 0) {
                //Method called to the next timestamp
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //If it is the first acquisition of the current time, it is set to 0
            sequence = 0;
        }

        //Record the last timestamp
        lastTimestamp = timestamp;

        //Offset calculation
        return ((timestamp - twepoch) << timestampLeftShift) |
            (datacenterId << datacenterIdShift) |
            (workerId << workerIdShift) |
            sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        //Get latest timestamp
        long timestamp = timeGen();
        //If the latest timestamp is found to be less than or equal to the timestamp whose serial number has exceeded 4095
        while (timestamp <= lastTimestamp) {
            //If not, continue
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        SnowFlake worker = new SnowFlake(1, 1);
        long timer = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            worker.nextId();
        }
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() - timer);
        for(int i=0; i<200; i++) {
            System.out.println( );
            System.out.println(worker.nextId());
        }
    }

}
