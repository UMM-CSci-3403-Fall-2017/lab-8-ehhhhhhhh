package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

	
	public ThreadedMinimumPairwiseDistance() {
		
	}


    @Override
    public int minimumPairwiseDistance(int[] values) {
        //throw new UnsupportedOperationException();
        
    	Answer answer = new Answer();
        
        Thread thread1 = new Thread(new LowerLeft(answer, values));
        Thread thread2 = new Thread(new BottomRight(answer, values));
        Thread thread3 = new Thread(new Middle(answer, values));
        Thread thread4 = new Thread(new TopRight(answer, values));
        
        Thread[] threadList = {thread1, thread2, thread3, thread4};
        
        for (int i=0; i<4; i++) {
        	threadList[i].start();
        }
        
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
                        
                        //cd++;
        				//System.out.println(cd);
                }
        }
    	System.out.println("LowerLeft took" + (System.currentTimeMillis() - startTime));
        answer.setAnswer(currentMin);
    	
    }
    }
   
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
                        
                        //cd++;
        				//System.out.println(cd);
                }
    		
    		}
    		System.out.println("BottomRight took" + (System.currentTimeMillis() - startTime));
        answer.setAnswer(currentMin);
    	}
    	
    }

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
                                
                                //cd++;
                				//System.out.println(cd);
                        }
                }
                System.out.println("Middle took" + (System.currentTimeMillis() - startTime));
                answer.setAnswer(currentMin);
        }

    }

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
                                
                                //cd++;
                				//System.out.println(cd);
                        }
                }
                System.out.println("TopRight took" + (System.currentTimeMillis() - startTime));
                answer.setAnswer(currentMin);
        
        }

    }


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
