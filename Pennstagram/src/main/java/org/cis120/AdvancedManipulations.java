package org.cis120;

public class AdvancedManipulations {

    /**
     * Change the contrast of a picture.
     *
     * Your job is to change the intensity of the colors in the picture.
     * The simplest method of changing contrast is as follows:
     *
     * 1. Find the average color intensity of the picture.
     * a) Sum the values of all the color components for each pixel.
     * b) Divide the total by the number of pixels times the number of
     * components (3).
     * 2. Subtract the average color intensity from each color component of
     * each pixel. Note that you could underflow into negatives.
     * This will make the average color intensity zero.
     * 3. Scale the intensity of each pixel's color components by multiplying
     * them by the "multiplier" parameter. Note that the multiplier is a
     * double (a decimal value like 1.2 or 0.6) and color values are ints
     * between 0 and 255.
     * 4. Add the original average color intensity back to each component of
     * each pixel.
     * 5. Clip the color values so that all color component values are between
     * 0 and 255. (This should be handled by the Pixel class anyway!)
     *
     * Hint: You should use Math.round() before casting to an int for
     * the average color intensity and for the scaled RGB values.
     * (I.e., in particular, the average should be rounded to an int
     * before being used for further calculations..)
     *
     * @param pic        the original picture
     * @param multiplier the factor by which each color component
     *                   of each pixel should be scaled
     * @return the new adjusted picture
     * 
     */
    public static PixelPicture adjustContrast(
            PixelPicture pic, double multiplier
    ) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] bmp = pic.getBitmap();

        // Record the sum of all color components in all pixels
        int sumC = 0;

        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                Pixel p = bmp[row][col];
                int colors = p.getRed() + p.getGreen() + p.getBlue();
                sumC += colors;
            }
        }

        // Calculate average color intensity
        int avgInt = (int) Math.round(sumC / (w * h * 3.0));

        // First calculating "normalized" color component.
        // Then scale each component.
        // Finally, add the average color intensity back.
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                Pixel p = bmp[row][col];
                // Normalizing each component
                int normR = p.getRed() - avgInt;
                int normG = p.getGreen() - avgInt;
                int normB = p.getBlue() - avgInt;

                // Scaling each normalized component
                int scaleR = (int) Math.round(normR * multiplier);
                int scaleG = (int) Math.round(normG * multiplier);
                int scaleB = (int) Math.round(normB * multiplier);

                // Adding the original average color intensity back
                int rC = scaleR + avgInt;
                int gC = scaleG + avgInt;
                int bC = scaleB + avgInt;

                bmp[row][col] = new Pixel(rC, gC, bC);
            }
        }
        return new PixelPicture(bmp);
    }


    /**
     * Check if the input pixel is in the input pixel array.
     *
     * Iterate the first given number of pixels in the pixel array. Return true immediately
     * if the current pixel is distantly equal to the input pixel.
     *
     * @param pixelArr the pixel array we are checking
     * @param curr     the target pixel we want to find
     * @param length   the number of pixels we want to check in the pixel array
     * @return if the target pixel is in the particular segment of the pixel array
     */
    public static boolean inPalette(Pixel[] pixelArr, Pixel curr, int length) {
        for (int i = 0; i < length; i++) {
            if (curr.distance(pixelArr[i]) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reduce a picture to its most common colors.
     *
     * You will need to make use of the ColorMap class to generate a map from
     * Pixels of a certain color to the frequency with which pixels of that
     * color appear in the image. If you go to the ColorMap class, you will
     * notice that it does not have an explicitly declared constructor. In
     * those cases, Java provides a default constructor, which you can call
     * with no arguments as follows:
     * 
     * ColorMap m = new ColorMap();
     * 
     * You will then go on to populate your ColorMap by adding pixels and their
     * corresponding frequencies.
     * 
     * Once you have generated your ColorMap, select your palette by
     * retrieving the pixels whose color appears in the picture with the
     * highest frequency. Then change each pixel in the picture to one with
     * the closest matching color from your palette.
     *
     * Note that if there are two different colors that are the *same* minimal
     * distance from the given color, your code should select the most
     * frequently appearing one as the new color for the pixel. If both colors
     * appear with the same frequency, your code should select the one that
     * appears *first* in the output of the colormap's getSortedPixels.
     *
     * Algorithms like this are widely used in image compression. GIFs in
     * particular compress the palette to no more than 255 colors. The variant
     * we have implemented here is a weak one, since it only counts color
     * frequency by exact match. Advanced palette reduction algorithms (known as
     * "indexing" algorithms) calculate color regions and distribute the palette
     * over the regions. For example, if our picture had a lot of shades of blue
     * and a little bit of red, our algorithm would likely choose a palette of
     * all blue colors. An advanced algorithm would recognize that blues look
     * similar and distribute the palette so that it would be possible to
     * display red as well.
     *
     * @param pic       the original picture
     * @param numColors the maximum number of colors that can be used in the
     *                  reduced picture
     * @return the new reduced picture
     */

    public static PixelPicture reducePalette(PixelPicture pic, int numColors) {
        ColorMap m = new ColorMap();
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] bmp = pic.getBitmap();
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                Pixel p = bmp[row][col];
                if (!(m.contains(p))) {
                    m.put(p, 1);
                } else {
                    m.put(p, m.getValue(p) + 1);
                }
            }
        }

        // Sort the color map
        Pixel[] pixelArr = m.getSortedPixels();

        // Change each pixel in the picture
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                Pixel p = bmp[row][col];

                // Record the most similar pixel in the palette and its frequency
                Pixel paletteP = pixelArr[0];

                // If current pixel is not in palette,
                // iterate through the palette array to update the most similar pixel
                if (! (inPalette(pixelArr, p, numColors))) {
                    for (int i = 0; i < numColors; i++) {
                        if (p.distance(paletteP) > p.distance(pixelArr[i])) {
                            paletteP = pixelArr[i];
                        } else if (p.distance(paletteP) == p.distance(pixelArr[i])) {
                            // If the distances are same, record the pixel with higher frequency
                            if (m.getValue(pixelArr[i]) > m.getValue(paletteP)) {
                                paletteP = pixelArr[i];
                            }
                            // If less frequent than paletteP or frequencies are same, do not update
                        } // If paletteArr[i] has larger distance, do not update
                    }
                    bmp[row][col] = paletteP;
                }
            }
        }

        return new PixelPicture(bmp);
    }

    /**
     * This method blurs an image.
     *
     * PLEASE read about the *required* division implementation below - even
     * if you understand the rest of the implementation, slight floating-point
     * errors can cause significant autograder deductions!
     *
     * The general idea is that to determine the color of a pixel at
     * coordinate (x, y) of the result, look at (x, y) in the input image
     * as well as the pixels within a box (details below) centered at (x, y).
     * The average color of the pixels in the box - determined by separately
     * averaging R, G, and B - will be the color of (x, y) in the result.
     *
     * How big is the box? That's defined by {@code radius}. A radius of 1
     * yields a 3x3 box (all pixels 1 step away, including diagonals).
     * Similarly, a radius of 2 yields a 5x5 box, a radius of 3 a 7x7 box, etc.
     *
     * As an example, say we have the following image - each pixel is written
     * as (r, g, b) - and the radius parameter is 1.
     *
     * ( 1, 13, 25) ( 2, 14, 26) ( 3, 15, 27) ( 4, 16, 28)
     * ( 5, 17, 29) ( 6, 18, 30) ( 7, 19, 31) ( 8, 20, 32)
     * ( 9, 21, 33) (10, 22, 34) (11, 23, 35) (12, 24, 36)
     *
     * If we wanted the color of the output pixel at (1, 1), we would look at
     * the radius-1 box surrounding (1, 1) in the original image, which is
     *
     * ( 1, 13, 25) ( 2, 14, 26) ( 3, 15, 27)
     * ( 5, 17, 29) ( 6, 18, 30) ( 7, 19, 31)
     * ( 9, 21, 33) (10, 22, 34) (11, 23, 35)
     *
     * The average red component is (1 + 2 + ... + 9) / 9 = 5, so the result
     * pixel at (1, 1) should have red component 5.
     *
     * If the target pixel is on the edge, you should average the pixels
     * within the radius that exist. So in the same example above, the color of
     * the output at (0, 0) would be the average of:
     *
     * ( 1, 13, 25) ( 2, 14, 26)
     * ( 5, 17, 29) ( 6, 18, 30)
     *
     * **IMPORTANT FLOATING POINT NOTE:** To compute the average in a way that's
     * compatible with our autograder, please do the following steps in order:
     *
     * 1. Use floating-point division (not integer division) to divide the
     * total red/green/blue amounts by the number of pixels.
     * 2. Use Math.round() on the result of 1. This is still a float, but it
     * has been rounded to the nearest integer value.
     * 3. Cast the result of 2 to an int. That should be the component's value
     * in the output picture.
     *
     * @param pic    The picture to be blurred.
     * @param radius The radius of the blurring box.
     * @return A blurred version of the original picture.
     */
    public static PixelPicture blur(PixelPicture pic, int radius) {
        int w = pic.getWidth();
        int h = pic.getHeight();

        Pixel[][] bmp = pic.getBitmap();
        Pixel[][] newBmp = pic.getBitmap();
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                int rSum = 0;
                int gSum = 0;
                int bSum = 0;
                int pNum = 0;

                // Calculate the dimension of the box
                int minC = Math.max(0, col - radius); // min column index
                int minR = Math.max(0, row - radius); // min row index
                int maxC = Math.min(w - 1, col + radius); // max column index
                int maxR = Math.min(h - 1, row + radius); // max row index

                // Iterate the box and update sums
                for (int i = minR; i <= maxR; i++) {
                    for (int j = minC; j <= maxC; j++) {
                        Pixel p = bmp[i][j];
                        rSum += p.getRed();
                        gSum += p.getGreen();
                        bSum += p.getBlue();
                        pNum += 1;
                    }
                }

                // Find averages
                int rAvg = (int) Math.round((0.0 + rSum) / pNum);
                int gAvg = (int) Math.round((0.0 + gSum) / pNum);
                int bAvg = (int) Math.round((0.0 + bSum) / pNum);

                newBmp[row][col] = new Pixel(rAvg, gAvg, bAvg);
            }
        }
        return new PixelPicture(newBmp);
    }

    // You may want to add a static helper function here to
    // help find the average color around the pixel you are blurring.

    /**
     * Challenge Problem (this problem is worth 0 points):
     * Flood pixels of the same color with a different color.
     *
     * The name is short for flood fill, which is the familiar "paint bucket"
     * operation in graphics programs. In a paint program, the user clicks on a
     * point in the image. Every neighboring, similarly-colored point is then
     * "flooded" with the color the user selected.
     *
     * Suppose we want to flood color at (x,y). The simplest way to do flood
     * fill is as follows:
     *
     * 1. Let target be the color at (x,y).
     * 2. Create a set of points Q containing just the point (x,y).
     * 3. Take the first point p out of Q.
     * 4. Set the color at p to color.
     * 5. For each of p's non-diagonal neighbors - up, down, left, and right -
     * check to see if they have the same color as target. If they do, add
     * them to Q.
     * 6. If Q is empty, stop. Otherwise, go to 3.
     *
     * This is a naive algorithm that can be made significantly faster if you
     * wish to try.
     *
     * For Q, you should use the provided IntQueue class. It works very much
     * like the queues we implemented in OCaml.
     *
     * @param pic The original picture to be flooded.
     * @param c   The pixel the user "clicked" (representing the color that should
     *            be flooded).
     * @param row The row of the point on which the user "clicked."
     * @param col The column of the point on which the user "clicked."
     * @return A new picture with the appropriate region flooded.
     */
    public static PixelPicture flood(PixelPicture pic, Pixel c, int row, int col) {
        return pic;
    }
}
