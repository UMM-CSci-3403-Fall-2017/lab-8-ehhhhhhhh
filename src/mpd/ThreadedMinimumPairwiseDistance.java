package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

	
	public ThreadedMinimumPairwiseDistance() {
		
	}


    @Override
    public int minimumPairwiseDistance(int[] values) {
        
    	//Declare the shared answer object
	Answer answer = new Answer();
        
	// Create threads for each section of the problem space
        Thread thread1 = new Thread(new LowerLeft(answer, values));
        Thread thread2 = new Thread(new BottomRight(answer, values));
        Thread thread3 = new Thread(new Middle(answer, values));
        Thread thread4 = new Thread(new TopRight(answer, values));
       
        Thread[] threadList = {thread1, thread2, thread3, thread4};
        
        // Start the threads	
        for (int i=0; i<4; i++) {
        	threadList[i].start();
        }
        
	// Wait until all threads are completed to return answer
        for (int i=0; i<4; i++) {
        	try {
        		threadList[i].join();
        	} catch (InterruptedException e) {
        		System.out.println("Thread Interrupted");
        	}
        	
        }
        System.out.println("finished array");
        return answer.getAnswer();
        
    }
    
    // Class for the lower left portion of the problem space
    private class LowerLeft implements Runnable {
    	
    	Answer answer;
    	int[] values;
    	
    	int currentMin = Integer.MAX_VALUE;
    	long startTime = System.currentTimeMillis();
    	
    	public LowerLeft(Answer answer, int[] values) {
    		
    		this.answer = answer;
    		this.values = values;
    	}
    	
    	public void run() {
    		for (int i = 0; i < values.length / 2; i++) {
    			for (int j = 0; j < i; j++) {
    				if (Math.abs(values[i] - values[j]) < currentMin) {
                		currentMin = Math.abs(values[i] - values[j]);
                		
                	}
                        
                }
        }
    	System.out.println("LowerLeft took" + (System.currentTimeMillis() - startTime));
        answer.setAnswer(currentMin);
    	
    }
    }
    // lower right portion of the problem space 
    private class BottomRight implements Runnable {
    	
    	Answer answer;
    	int[] values;
    	int currentMin = Integer.MAX_VALUE;
    	long startTime = System.currentTimeMillis();
    	
    	public BottomRight(Answer answer, int[] values) {
    		
    		this.answer = answer;
    		this.values = values;
    	}
    	
    	public void run() {
    		for (int i = values.length/2; i < values.length; i++) {
    			for (int j = 0; j+values.length/2 < i - 1; j++) {
    				if (Math.abs(values[i] - values[j]) < currentMin) {
                		currentMin = Math.abs(values[i] - values[j]);
                		
                	}
                        
                }
    		
    		}
    	System.out.println("BottomRight took" + (System.currentTimeMillis() - startTime));
        answer.setAnswer(currentMin);
    	}
    	
    }
    
    // middle section of the problem space
    private class Middle implements Runnable {

        Answer answer;
        int[] values;
        long startTime = System.currentTimeMillis();

        int currentMin = Integer.MAX_VALUE;
        
        public Middle(Answer answer, int[] values) {

                this.answer = answer;
                this.values = values;
        }

        public void run() {
                for (int i = values.length/2; i < values.length; i++) {
                        for (int j = i-values.length/2; j < values.length/2; j++) {
                        	if (Math.abs(values[i] - values[j]) < currentMin) {
                        		currentMin = Math.abs(values[i] - values[j]);
                        	}
                        }
                }
                System.out.println("Middle took" + (System.currentTimeMillis() - startTime));
                answer.setAnswer(currentMin);
        }

    }
    
    // top section of the problem space
    private class TopRight implements Runnable {

        Answer answer;
        int[] values;

        long startTime = System.currentTimeMillis();
        
        int currentMin = Integer.MAX_VALUE;
        
        public TopRight(Answer answer, int[] values) {

                this.answer = answer;
                this.values = values;
        }

        public void run() {
                for (int i = values.length/2; i < values.length; i++) {
                        for (int j = values.length/2; j < i; j++) {
                        	if (Math.abs(values[i] - values[j]) < currentMin) {
                        		currentMin = Math.abs(values[i] - values[j]);
                        	}
                        }
                }
                System.out.println("TopRight took" + (System.currentTimeMillis() - startTime));
                answer.setAnswer(currentMin);
        
        }

    }

    // Shared inner class storing minimum pair distance
    private class Answer {
    	
    	private int content;
    	
    	public Answer() {
    		content = Integer.MAX_VALUE;
    	}
    	
    	public int getAnswer() {
    		return content;
    	}
    	
    	public synchronized void setAnswer(int newAnswer) {
    		if (newAnswer < content) {
    			content = newAnswer;
    		}
    	}
    }

}
