package org.dyn4j.game2d.testbed;

/**
 * Class used to track various usage information for the TestBed.
 * <p>
 * The percentages are updated every second.
 * @author William Bittle
 */
public class Usage {
	/** One second in nanoseconds */
	private static final double ONE_SECOND_IN_NANOSECONDS = 1.0E9;
	
	/** The elapsed time since the last evaluation */
	private long elapsedTime;
	
	/** The time used for rendering */
	private long renderTime;
	
	/** The time used for input polling */
	private long inputTime;
	
	/** The time used for updating */
	private long updateTime;
	
	/** The time used by the system */
	private long systemTime;
	
	/** The total memory */
	private long totalMemoryAcc;
	
	/** The free memory */
	private long freeMemoryAcc;
	
	/** The number of iterations */
	private long iterations;
	
	/** Last second's render to total time percentage */
	private double renderTimePercentage;
	
	/** Last second's update to total time percentage */
	private double updateTimePercentage;
	
	/** Last second's input to total time percentage */
	private double inputTimePercentage;
	
	/** Last second's system to total time percentage */
	private double systemTimePercentage;
	
	/** Last second's used memory percentage */
	private double usedMemoryPercentage;
	
	/** Last second's free memory percentage */
	private double freeMemoryPercentage;
	
	/** Last second's total memory */
	private double totalMemory;
	
	/**
	 * Updates the elapsed time and performs an evaluation of
	 * usage at one second intervals.
	 * @param elapsedTime the elapsed time since the last update in nanoseconds
	 */
	public void update(long elapsedTime) {
		// increment the total time
		this.elapsedTime += elapsedTime;
		// increment the total and free memory
		Runtime runtime = Runtime.getRuntime();
		this.totalMemoryAcc += runtime.totalMemory();
		this.freeMemoryAcc += runtime.freeMemory();
		this.iterations++;
		// has it been a second?
		if (this.elapsedTime >= Usage.ONE_SECOND_IN_NANOSECONDS) {
			// calculate the system time
			this.systemTime = this.elapsedTime - (this.renderTime + this.inputTime + this.updateTime);
			// calculate the percentages of the total time
			this.renderTimePercentage = (double) this.renderTime / (double) this.elapsedTime;
			this.updateTimePercentage = (double) this.updateTime / (double) this.elapsedTime;
			this.inputTimePercentage = (double) this.inputTime / (double) this.elapsedTime;
			if (this.systemTime > 0) {
				this.systemTimePercentage = (double) this.systemTime / (double) this.elapsedTime;
			} else {
				this.systemTimePercentage = 0;
			}
			// calculate the percentage of memory free/used
			this.totalMemory = (double) this.totalMemoryAcc / (double) this.iterations;
			double freeMem = (double) this.freeMemoryAcc / (double) this.iterations;
			double usedMem = this.totalMemory - freeMem;
			this.freeMemoryPercentage = freeMem / this.totalMemory;
			this.usedMemoryPercentage = usedMem / this.totalMemory;
			this.elapsedTime = 0;
			this.renderTime = 0;
			this.updateTime = 0;
			this.inputTime = 0;
		}
	}
	
	/**
	 * Increments the total render time by the given
	 * elapsed time in nanoseconds.
	 * @param elapsedTime the elapsed time in nanoseconds
	 */
	public void renderComplete(long elapsedTime) {
		this.renderTime += elapsedTime;
	}
	
	/**
	 * Increments the total input time by the given
	 * elapsed time in nanoseconds.
	 * @param elapsedTime the elapsed time in nanoseconds
	 */
	public void setInput(long elapsedTime) {
		this.inputTime += elapsedTime;
	}
	
	/**
	 * Increments the total update time by the given
	 * elapsed time in nanoseconds.
	 * @param elapsedTime the elapsed time in nanoseconds
	 */
	public void setUpdate(long elapsedTime) {
		this.updateTime += elapsedTime;
	}
	
	/**
	 * Returns the render time as a percentage of the total time.
	 * @return double
	 */
	public double getRenderTimePercentage() {
		return this.renderTimePercentage;
	}
	
	/**
	 * Returns the input time as a percentage of the total time.
	 * @return double
	 */
	public double getInputTimePercentage() {
		return this.inputTimePercentage;
	}
	
	/**
	 * Returns the update time as a percentage of the total time.
	 * @return double
	 */
	public double getUpdateTimePercentage() {
		return this.updateTimePercentage;
	}
	
	/**
	 * Returns the system time as a percentage of the total time.
	 * @return double
	 */
	public double getSystemTimePercentage() {
		return this.systemTimePercentage;
	}
	
	/**
	 * Returns the free memory as a percentage of the total memory available.
	 * @return double
	 */
	public double getFreeMemoryPercentage() {
		return this.freeMemoryPercentage;
	}
	
	/**
	 * Returns the used memory as a percentage of the total memory available.
	 * @return double
	 */
	public double getUsedMemoryPercentage() {
		return this.usedMemoryPercentage;
	}
	
	/**
	 * Returns the total memory.
	 * @return double
	 */
	public double getTotalMemory() {
		return this.totalMemory;
	}
}
