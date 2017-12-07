package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

	
	public ThreadedMinimumPairwiseDistance() {
	}
	
	public int minimumPairwiseDistance(int[] values, int currentStart, int currentEnd, Answer answer) {
		
		int maxThreads = 10000;
		int numThreads = (currentEnd - currentStart)/2;
		int totalThreads = (currentEnd - currentStart)/2;
		
		Thread[] threadList;
        if (maxThreads <= numThreads) {
        	numThreads = maxThreads; 
        }
        threadList = new Thread[numThreads];
              
        for(int i = 0; i<numThreads; i++) {
        	Thread thread = new Thread(new innerThread(currentStart, currentEnd, answer, values),"thread"+i);
        	threadList[i] = thread;
        	currentStart++;
        	currentEnd--;
        }
        
        for(int i = 0; i<numThreads; i++) {
        	threadList[i].start();
        	
        }
        
        for (int i=0; i<numThreads; i++) {
        	try {
        		threadList[i].join();
        	} catch (InterruptedException e) {
        		System.out.println("Thread Interrupted");
        	}
        	
        }
        System.out.println("Finished" + numThreads + " threads.");
        if (numThreads != totalThreads) {
        	return minimumPairwiseDistance(values, currentStart, currentEnd, answer);
        }
        else{
        	return answer.getAnswer();
        }
		
	}
	
    @Override
    public int minimumPairwiseDistance(int[] values) {
        //throw new UnsupportedOperationException();
        
    	Answer answer = new Answer();
        int endPoint = values.length - 1;
        int startPoint = 0;
        int numThreads = values.length/2;
        int maxThreads = 10000;
        int totalThreads = values.length/2;
        
        Thread[] threadList;
        if (maxThreads <= numThreads) {
        	numThreads = maxThreads; 
        }
        threadList = new Thread[numThreads];
              
        for(int i = 0; i<numThreads; i++) {
        	Thread thread = new Thread(new innerThread(startPoint, endPoint, answer, values),"thread"+i);
        	threadList[i] = thread;
        	startPoint++;
        	endPoint--;
        }
        
        for(int i = 0; i<numThreads; i++) {
        	threadList[i].start();
        	
        }
        
        for (int i=0; i<numThreads; i++) {
        	try {
        		threadList[i].join();
        	} catch (InterruptedException e) {
        		System.out.println("Thread Interrupted");
        	}
        	
        }
        
        System.out.println("Finished" + numThreads + " threads.");
        if (numThreads != totalThreads) {
        	return minimumPairwiseDistance(values, startPoint, endPoint, answer);
        }
        return answer.getAnswer();
        
    }
    
    private class innerThread implements Runnable {
    	
    	int prob1;
    	int prob2;
    	Answer answer;
    	int[] values;
    	
    	public innerThread(int prob1, int prob2, Answer answer, int[] values) {
    		
    		this.prob1 = prob1;
    		this.prob2 = prob2;
    		this.answer = answer;
    		this.values = values;
    		
    	}
    	
    	public void run() {
    		
    		int i = prob1;
    		
    		for (int j = 0; j < i; j++) {
    			int diff = Math.abs(values[i] - values[j]);
    			answer.setAnswer(diff);
    		}
    		
    		i = prob2;
    		
    		for (int j = 0; j < i; j++) {
    			int diff = Math.abs(values[i] - values[j]);
    			answer.setAnswer(diff);
    		}
    		
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
