package com.happyprog.pulse;

import org.eclipse.jdt.junit.JUnitCore;
import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestElement.Result;
import org.eclipse.jdt.junit.model.ITestRunSession;

public class JUnitSubscriber implements TestSubscriber {

	private TestObserver observer;

	@Override
	public void subscribe(final TestObserver observer) {
		this.observer = observer;
		addTestRunListener();
	}

	void reportResults(Result result) {
		if (!result.equals(ITestElement.Result.OK)) {
			observer.onFailingTests();
		} else {
			observer.onPassingTests();
		}
	}

	void addTestRunListener() {
		JUnitCore.addTestRunListener(new RunListener());
	}

	class RunListener extends TestRunListener {
		@Override
		public void sessionFinished(ITestRunSession session) {
			super.sessionFinished(session);
			reportResults(session.getTestResult(true));
		}
	}

}
