import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

public class RandomShuffler {

	private static double INIT_THRESHOLD = 0.99;
	private static double FULL_THRESHOLD = INIT_THRESHOLD;

	public static boolean Shuffle(FlowCell fc) throws IOException {

		int attempts = 0;
//		while (++attempts < fc.getLanes().size()) {
//			randomSwap(fc);
//
//		}


		double rawScore, p1Score, p2Score, s1Score, s2Score, s3Score;

		rawScore = fc.calculateFlowCellScore();

		double prevScore = rawScore;
		Polish(fc);


		int polishIter = 0;
		double currScore = fc.calculateFlowCellScore();
//		while (currScore > prevScore) {
//			System.out.printf("P1: Going again as prevScore: %.2f > %.2f\n",
//					currScore, prevScore);
//			// add to score
//			while (polishIter >= Scores.p.size()) {
//				Scores.p.add(new Double(0.0f));
//			}
//
//			Scores.p.set(polishIter, Scores.p.get(polishIter)
//					+ (currScore - prevScore));
//
//			prevScore = currScore;
////			Polish2(fc);
//			// if best, set it
//			if (Scores.best == null
//					|| (fc.calculateFlowCellScore() > Scores.best
//							.calculateFlowCellScore() && fc.NumNonEmptyLanes() <= Scores.best
//							.NumNonEmptyLanes())) {
//				Scores.best = fc;
//
//				Scores.best.printFlowCell();
//			}
//			currScore = fc.calculateFlowCellScore();
//			// fc.printFlowCell();
//			++polishIter;
//		}

		prevScore = currScore;
		PolishSwap(fc);
		// fc.printFlowCell();
		// PolishSwap(fc);
		// PolishSwap(fc);
		// PolishSwap(fc);
		// PolishSwap(fc);

		int swapIter = 0;
		currScore = fc.calculateFlowCellScore();
		while (currScore > prevScore) {
			// add to score
			// System.out.printf("S1: Going again as prevScore: %.2f > %.2f\n",currScore,prevScore);
			while (swapIter >= Scores.s.size()) {
				Scores.s.add(new Double(0.0f));
			}

			Scores.s.set(swapIter, Scores.s.get(swapIter)
					+ (currScore - prevScore));

			prevScore = currScore;
			PolishSwap(fc);
			// if best, set it
			if (Scores.best == null
					|| (fc.calculateFlowCellScore() > Scores.best
							.calculateFlowCellScore() && fc.NumNonEmptyLanes() <= Scores.best
							.NumNonEmptyLanes())) {
				Scores.best = fc;
				Scores.best.printFlowCell();
			}
			currScore = fc.calculateFlowCellScore();
//			fc.printFlowCell();
			++swapIter;
		}

		prevScore = currScore;
//		Polish(fc);
		// fc.printFlowCell();
//		polishIter = 0;
//		currScore = fc.calculateFlowCellScore();
//		while (currScore > prevScore) {
//			// System.out.printf("P1: Going again as prevScore: %.2f > %.2f\n",currScore,prevScore);
//			// add to score
//			while (polishIter >= Scores.p2.size()) {
//				Scores.p2.add(new Double(0.0f));
//			}
//
//			Scores.p2.set(polishIter, Scores.p2.get(polishIter)
//					+ (currScore - prevScore));
//
//			prevScore = currScore;
////			Polish2(fc);
//			// if best, set it
//			if (Scores.best == null
//					|| (fc.calculateFlowCellScore() > Scores.best
//							.calculateFlowCellScore() && fc.NumNonEmptyLanes() <= Scores.best
//							.NumNonEmptyLanes())) {
//				Scores.best = fc;
//				Scores.best.printFlowCell();
//			}
//			currScore = fc.calculateFlowCellScore();
//			// fc.printFlowCell();
//			++polishIter;
//		}

//		prevScore = currScore;
//		PolishSwap(fc);
//		// fc.printFlowCell();
//		swapIter = 0;
//		currScore = fc.calculateFlowCellScore();
//		while (currScore > prevScore) {
//			// System.out.printf("S2: Going again as prevScore: %.2f > %.2f (normalised:\t%.2f)\n",currScore,prevScore,
//			// currScore/(double)(fc.NumLanes()));;
//			// add to score
//			while (swapIter >= Scores.s2.size()) {
//				Scores.s2.add(new Double(0.0f));
//			}
//
//			Scores.s2.set(swapIter, Scores.s2.get(swapIter)
//					+ (currScore - prevScore));
//
//			prevScore = currScore;
////			Polish(fc);
//			PolishSwap(fc);
//			// if best, set it
//			if (Scores.best == null
//					|| (fc.calculateFlowCellScore() > Scores.best
//							.calculateFlowCellScore() && fc.NumNonEmptyLanes() <= Scores.best
//							.NumNonEmptyLanes())) {
//				Scores.best = fc;
//				Scores.best.printFlowCell();
//			}
//
//			// fc.printFlowCell();
//			currScore = fc.calculateFlowCellScore();
//			++swapIter;
//		}

		Scores.counts++;
		Scores.report();

		System.out.println("SUCCESS!");
		
		//sort best
		Collections.sort(Scores.best.getLanes(), new LaneFullnessComparator());
		return true;
	}



	public static boolean Polish(FlowCell fc) throws IOException {

//		System.out.printf("Loop on EDT? %s\n",javax.swing.SwingUtilities.isEventDispatchThread());
//		System.in.read();
		// sort lanes from most full to least full
		ArrayList<Lane> sortedLanes = fc.NonEmptyLanes();
		Collections.sort(sortedLanes, new LaneFullnessComparator());


		// starting from full to least full, try and squeeze samples into gap on fullestlane
		boolean donePolishing = false;

		ListIterator<Lane> iFuller = sortedLanes.listIterator(sortedLanes.size());
		while (!donePolishing && iFuller.hasPrevious()) {
			
			//update best flowcell if required
			Scores.updateBest(fc);

			Lane fullest = iFuller.previous();
			
			if(fullest.isEmpty()){
				break;
			}

			
			ListIterator<Lane> iEmptier = sortedLanes.listIterator();

			while (iEmptier.hasNext()/* && !searchForGapComplete */) {
				Lane emptiest = iEmptier.next();
				Scores.updateBest(fc);
				if(fullest.LaneNumber()!=emptiest.LaneNumber()){
				
					// check each sample in lane to see if any will fit in gap in
					// fullest lane
					
					//first, try shifting pools rather than individual samples
					boolean moreBundlesToProcess = true;
					while(moreBundlesToProcess){
						moreBundlesToProcess=false;
						emptiest.calculatePools();
						for(SampleBundle iPool : emptiest.Pools().values()){
							if (iPool.Size() <= fullest.remainingCapacity() && 
								fullest.getSharedBarcodes(iPool.Samples()).size()==0 &&
								fullest.currentFillLevel() + iPool.Size() > emptiest.currentFillLevel()) {
	//							System.out.printf("POOL MOVING: %d[%.2f]->%d[%.2f (%.2f)]\n",emptiest.LaneNumber(), iPool.Size(), fullest.LaneNumber(), fullest.currentFillLevel(), fullest.remainingCapacity());
	//							System.in.read();
								if(fullest.LaneNumber()!=emptiest.LaneNumber()){//TODO: This is in a ridiculous place. Put it BEFORE doing the more heavy calculations
									fullest.addBundle(iPool);
									fullest.calculatePools(); //best to recalculate as soon as something changes
									
									for(Sample iSampleToRemove : iPool.Samples()){	//TODO: This could be better with a removePool(int) method
										emptiest.removeSample(iSampleToRemove);
									}
									emptiest.calculatePools();
									moreBundlesToProcess=true;
								}
							}
						}		
					}
				
					
					//Now do individual samples
					Iterator<Sample> iSample = emptiest.getSamples().iterator();
					// ArrayList<Sample> samplesToRemove = new ArrayList<Sample>();
					// //store samples for erasing
					while (iSample.hasNext()) {
						Scores.updateBest(fc);
						Sample sample = iSample.next();
						if (sample.Reads() <= fullest.remainingCapacity() && 
							!sample.isPooled() &&
							!fullest.hasBarcode(sample.Barcode()) &&
							fullest.currentFillLevel() + sample.Reads() > emptiest.currentFillLevel()) {
								
								
								
							// donePolishing=true; //just one iteration for
							// debugging purposes
	
							// final check that we're not comparing lane with itself
							if (fullest.LaneNumber() != emptiest.LaneNumber()) {	//TODO: This is in a ridiculous place. Put it BEFORE doing the more heavy calculations
								// System.out.printf("Found slot [%d : %.2f]-> [%d : %.2f]\n",emptiest.LaneNumber(),
								// sample.Reads(), fullest.LaneNumber(),
								// fullest.remainingCapacity());
	//							searchForGapComplete = true;
								fullest.addSample(sample);
								iSample.remove();
								
								if (Scores.best == null
										|| (fc.calculateFlowCellScore() > Scores.best.calculateFlowCellScore())) {
									Scores.best = fc;
									Scores.best.printFlowCell();
									Scores.updateBest(fc);
								}
								// fc.printFlowCell();
								// samplesToRemove.add(sample);
							} else {
								// System.out.printf("SAME  slot [%d : %.2f]-> [%d : %.2f]\n",emptiest.LaneNumber(),
								// sample.Reads(), fullest.LaneNumber(),
								// fullest.remainingCapacity());
							}
	
						}
	
						// Iterator<Sample> remSam = samplesToRemove.iterator();
						// while(remSam.hasNext()){
						// emptiest.removeSample(remSam.next());
						// }
	
					}
				}

			}

		}
		// attempt to find something from other lanes that will fill this gap

		// if(Scores.best == null || fc.calculateFlowCellScore() >
		// Scores.best.calculateFlowCellScore()){
		// Scores.best = fc;
		// }
		return true;
	}
	


	public static boolean PolishSwap(FlowCell fc) throws IOException {
		// System.out.println(" *** POLISH SWAP *** ");
		// fc.printFlowCell();
		// sort lanes from full->empty
		ArrayList<Lane> sortedLanes = fc.NonEmptyLanes();
		Collections.sort(sortedLanes, new LaneFullnessComparator());

		// System.out.printf("Test: Fullest lane is: %.2f\n",sortedLanes.get(sortedLanes.size()-1).currentFillLevel());

		// starting with fullest lane and working towards least full, see if any
		// other lane has anything it can take
		ListIterator<Lane> iFuller = sortedLanes.listIterator(sortedLanes.size());
		while (iFuller.hasPrevious()) {
			boolean doneForThisSample = false;
			Lane fuller = iFuller.previous();
			
			//only continue if not totally full! //TODO: Change this so min size left required is size of smallest sample?
			if (fuller.currentFillLevel() > 0.99 * fuller.Capacity()){
				continue;
			}

			// System.out.printf("Attempting to gap fill on lane %d (%.2f)",fuller.LaneNumber(),
			// fuller.currentFillLevel());
			// fuller.printLane();
			// System.out.println();

			// Calculate bundles from fuller
			ArrayList<SampleBundle> fullerBundles = fuller.calculateSampleBundlePermutations();
			// Consider swapping out each sample in turn from fullest and see if
			// a better one can be found

			// starting with the emptiest lane and working towards the fullest,
			// check for bundles to grab
			Iterator<Lane> iEmptier = sortedLanes.iterator();
			while (iEmptier.hasNext()) {

				Lane emptier = iEmptier.next();

				// don't proceed if emptier == fuller!
				if (emptier.LaneNumber() == fuller.LaneNumber()) {
					continue;
				}

				// check emptier isn't empty!
				if (emptier.isEmpty()) {
					continue;
				}
				// don't proceed if emptier is fuller than fuller!
				if (emptier.currentFillLevel() > fuller.currentFillLevel()) {
					continue;	//TODO This could probably safely be a break, not a continue
//					break;	
				}

				// System.out.printf("\tGrabbing from lane %d (%.2f)",emptier.LaneNumber(),
				// emptier.currentFillLevel());
				// emptier.printLane();
				// System.out.println();

				// if all is fine, calculate emptier bundles
				ArrayList<SampleBundle> emptierBundles = emptier.calculateSampleBundlePermutations();

				Iterator<SampleBundle> iFullerBundles = fullerBundles.iterator();

				// compare
				while (iFullerBundles.hasNext()) {

					SampleBundle fullerBundle = iFullerBundles.next();
					// System.out.printf("\t\tComparing bundle from fuller %.2f\n",fullerBundle.Size());
					Iterator<SampleBundle> iEmptierBundles = emptierBundles.iterator();

					while (iEmptierBundles.hasNext()) {
						SampleBundle emptierBundle = iEmptierBundles.next();
						// System.out.printf("\t\t\tTo emptier %.2f\n",emptierBundle.Size());
						// check (1) that replacing the sample with the bundle
						// would be better and (b) that it won't exceed
						// capacity)
						double fullerSizeAfterSwap = fuller.currentFillLevel() - fullerBundle.Size() + emptierBundle.Size();
						double emptierSizeAfterSwap = emptier.currentFillLevel() - emptierBundle.Size() + fullerBundle.Size();

						// System.out.println("______________________");
						// System.out.printf("Checking barcode swap\n");
						// fc.printFlowCell();
						// System.out.printf("Fuller: "); fuller.printLane();
						// System.out.printf("Single: %s\n",
						// fromFuller.Barcode());
						// System.out.printf("Emptier: "); emptier.printLane();
						// System.out.printf("bundle: "); bundle.printBundle();
						// System.out.printf("FullerSizeAfterSwap: %.2f\n",
						// fullerSizeAfterSwap);
						// System.out.printf("EmptierSizeAfterSwap: %.2f\n",
						// emptierSizeAfterSwap);
						if (fullerSizeAfterSwap < fuller.Capacity()
								&& fullerSizeAfterSwap > emptierSizeAfterSwap
								&& fullerSizeAfterSwap > fuller
										.currentFillLevel())
						// if(bundle.Size() - fromFuller.Reads() > 0 &&
						// bundle.Size() + fuller.currentFillLevel() -
						// fromFuller.Reads() <= fuller.Capacity())
						{
							// TODO: Check barcodes!!!
							// check barcode situation
							// 1) if bundle has same barcode as single, that's fine for the receiver of the single OR if emptier doesn't contain that barcode at all
							ArrayList<Sample> s2eSharedSamples = emptier.getSharedBarcodes(fullerBundle.Samples());
							boolean singleToEmptyOk = s2eSharedSamples.size() == (fullerBundle.getSharedBarcodes(emptierBundle.Samples()).size()); 
							// i.e. if number of barcodes shared between fBundle and empty is the same as number of barcodes shared between fBundle and eBundle
							// boolean singleToEmptyOk =
							// (bundle.containsBarcode(fromFuller.Barcode()) ||
							// !emptier.hasBarcode(fromFuller.Barcode()));
							// 2) if fuller has none of the barcodes contained
							// in bundle, that's ok to receive
							// OR if there is ONE shared barcode, but that
							// barcode in fuller is migrating out to emptier
							// (kind of a swap).
							ArrayList<Sample> sharedSamples = fuller.getSharedBarcodes(emptierBundle.Samples());
							boolean bundleToFullOk = (sharedSamples.size() == emptierBundle.getSharedBarcodes(fullerBundle.Samples()).size());

							// System.out.println("______________________");
							// System.out.printf("Checking barcode swap\n");
							// fc.printFlowCell();
							// System.out.printf("Fuller: ");
							// fuller.printLane();
							// System.out.printf("Single: %s\n",
							// fromFuller.Barcode());
							// System.out.printf("Emptier: ");
							// emptier.printLane();
							// System.out.printf("bundle: ");
							// bundle.printBundle();
							//							
							// System.out.printf("SingleToEmptyOk? %s\n",
							// singleToEmptyOk);
							// System.out.printf("BundleToFullOk?  %s\n",
							// bundleToFullOk);

							if (singleToEmptyOk && bundleToFullOk) {
								// addBundle
								if (!fuller.addBundle(emptierBundle)) {
									System.out
											.printf("ERROR: duplicate sample while adding emptier bundle to fuller\n");
									System.in.read();
								}
								for (int i = 0; i < emptierBundle.Samples()
										.size(); ++i) {
									emptier.removeSample(emptierBundle
											.Samples().get(i));
								}
								if (emptierBundle.Size() == 0) {
									System.out
											.println("Empty bundle from emptier!");
								}
								iEmptierBundles.remove();

								if (!emptier.addBundle(fullerBundle)) {
									System.out
											.printf("ERROR: duplicate sample while adding fuller bundle to empty\n");
									System.in.read();
								}
								for (int i = 0; i < fullerBundle.Samples()
										.size(); ++i) {
									fuller.removeSample(fullerBundle.Samples()
											.get(i));
								}

								// System.out.println("Successful swap!");
								// fc.printFlowCell();

								doneForThisSample = true;
							}
							// System.out.println("______________________");
							// System.in.read();
							// System.out.printf("Found match: Bundle ");
							// bundle.printBundle();
							// System.out.printf(" from lane %d (%.2f)",emptier.LaneNumber(),
							// emptier.currentFillLevel());
							// System.out.printf(" in exchange for %.2f from lane %d\n",fromFuller.Reads(),
							// fuller.LaneNumber());

							// System.in.read();

						}

						if (doneForThisSample)
							break;
					}
					if (doneForThisSample) {
						// // iFromFuller.remove();
						break;
					}

				}
				if (doneForThisSample) {
					// // iFromFuller.remove();
					break;
				}
			}
			if (doneForThisSample) {
				// // iFromFuller.remove();
				break;
			}

		}

		if (Scores.best == null
				|| (fc.calculateFlowCellScore() > Scores.best.calculateFlowCellScore())) {
			Scores.best = fc;
			Scores.best.printFlowCell();
			Scores.updateBest(fc);
		}
		return true;
	}
	
	
	
	
	
	

	public static boolean randomSwap(FlowCell fc) throws IOException {

		// Grab random sample from 2 random lanes and swap
		Lane lane1 = fc.Lane((int) (Math.random() * fc.NumLanes()));
		// System.out.printf("Grabbed L1 as lane %d\t currentFill: %.2f\tremaining %.2f \tFull? %s\n",lane1.LaneNumber(),
		// lane1.currentFillLevel(), lane1.remainingCapacity(),
		// lane1.currentFillLevel() / lane1.Capacity()>FULL_THRESHOLD);
		double l1attempts = 0;
		boolean l1IsFull = lane1.currentFillLevel() / lane1.Capacity() >= FULL_THRESHOLD;
		boolean l1Overflowing = lane1.remainingCapacity() < 0;
		boolean l1Good = l1IsFull && !l1Overflowing;
		while (l1Good || lane1.isEmpty()) {
			if (++l1attempts == 10) {
				// System.out.printf("%.3f\t%d\n",FULL_THRESHOLD,attempts);
				FULL_THRESHOLD -= 0.01;
				if (FULL_THRESHOLD < 0.9) {
					// System.out.printf("Failed\n");
					Scores.failSwap += 1;
					return false;
				}
				l1attempts = 0;
			}
			lane1 = fc.Lane((int) (Math.random() * fc.NumLanes()));

			l1IsFull = lane1.currentFillLevel() / lane1.Capacity() >= FULL_THRESHOLD;
			l1Overflowing = lane1.remainingCapacity() < 0;
			l1Good = l1IsFull && !l1Overflowing;
			// System.out.printf("Grabbed L1 as lane %d\t currentFill: %.2f\tremaining %.2f \tFull? %s\n",lane1.LaneNumber(),
			// lane1.currentFillLevel(), lane1.remainingCapacity(),
			// lane1.currentFillLevel() / lane1.Capacity()>FULL_THRESHOLD);
		}

		FULL_THRESHOLD = INIT_THRESHOLD;

		Lane lane2 = fc.Lane((int) (Math.random() * fc.NumLanes()));
		double l2attempts = 0;
		boolean l2IsFull = lane2.currentFillLevel() / lane2.Capacity() >= FULL_THRESHOLD;
		boolean l2Overflowing = lane2.remainingCapacity() < 0;
		boolean l2Good = l2IsFull && !l2Overflowing;
		while (l2Good || lane2.LaneNumber() == lane1.LaneNumber()
				|| lane2.isEmpty()) {
			if (++l2attempts == 10) {
				// System.out.printf("%.3f\n",FULL_THRESHOLD);
				FULL_THRESHOLD -= 0.01;
				if (FULL_THRESHOLD < 0.9) {
					Scores.failSwap += 1;
					return false;
				}
				l2attempts = 0;
			}
			lane2 = fc.Lane((int) (Math.random() * fc.NumLanes()));
			l2IsFull = lane2.currentFillLevel() / lane2.Capacity() >= FULL_THRESHOLD;
			l2Overflowing = lane2.remainingCapacity() < 0;
			l2Good = l2IsFull && !l2Overflowing;

		}

		Sample sample1 = lane1.getRandomSample();
		Sample sample2 = null;
		if (lane2.hasBarcode(sample1.Barcode())) {
			sample2 = lane2.getSampleByBarcode(sample1.Barcode());
		} else {
			sample2 = lane2.getRandomSample();
			int changeSample2Attempt = 0;
			while (lane1.hasBarcode(sample2.Barcode())) {
				sample2 = lane2.getRandomSample();
				if (++changeSample2Attempt == 100) {
					Scores.failSwap += 1;
					return false;
				}
			}
		}

		// System.out.printf("Swapping [%d-%s] <> [%d-%s]\n",lane1.LaneNumber(),sample1.Barcode(),
		// lane2.LaneNumber(),sample2.Barcode());
		// remove
		lane1.removeSample(sample1);
		lane2.removeSample(sample2);

		// add
		lane1.addSample(sample2);
		lane2.addSample(sample1);

		// System.out.printf("Success\n");
		return true;
	}

	public static boolean randomDonate(FlowCell fc) {
		// System.out.printf("randomDonate: ");
		int attempts = 0;
		Lane donor = fc.Lane((int) (Math.random() * fc.NumLanes()));
		while (donor.isEmpty()) {
			if (attempts++ > 10) {
				// System.out.printf("Failed\n");
				Scores.failDonate += 1;
				return false;
			}
			donor = fc.Lane((int) (Math.random() * fc.NumLanes()));
		}

		Sample donation = donor.getRandomSample();

		attempts = 0;
		Lane receiver = fc.Lane((int) (Math.random() * fc.NumLanes()));
		while (receiver.LaneNumber() == donor.LaneNumber()
				|| receiver.hasBarcode(donation.Barcode())) {
			if (attempts++ > 10) {
				Scores.failDonate += 1;
				return false;
			}

			receiver = fc.Lane((int) (Math.random() * fc.NumLanes()));

		}

		// finally, do swap
		receiver.addSample(donation);
		donor.removeSample(donation);

		// System.out.printf("Success\n");
		return true;
	}

}
