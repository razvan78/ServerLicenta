package server;

import org.opencv.core.Core;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;

public class IMGProcessor {

	
	public static int compareFeature(String filename1, String filename2) {
		int retVal = 0;
		long startTime = System.currentTimeMillis();

		//agaug biblioteca de OpenCV
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// incarc imaginile
		Mat img1 = Imgcodecs.imread(filename1, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Mat img2 = Imgcodecs.imread(filename2, Imgcodecs.CV_LOAD_IMAGE_COLOR);

		
		MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
		MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
		

		FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
	
		detector.detect(img1, keypoints1);
		detector.detect(img2, keypoints2);

		DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		Mat descriptors1 = new Mat();
		Mat descriptors2 = new Mat();
		extractor.compute(img1, keypoints1, descriptors1);
		extractor.compute(img2, keypoints2, descriptors2);

		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

		if (descriptors2.cols() == descriptors1.cols()) {
			MatOfDMatch matches = new MatOfDMatch();
			matcher.match(descriptors1, descriptors2, matches);

			DMatch[] match = matches.toArray();
	
			for (int i = 0; i < descriptors1.rows(); i++) {
				if (match[i].distance <= 15) {
					retVal++;
				}
			}
		}

		long estimatedTime = System.currentTimeMillis() - startTime;

		return retVal;
	}
}
