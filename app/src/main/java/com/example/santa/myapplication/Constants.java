/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.santa.myapplication;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public final class Constants {

	public static final String[] IMAGES = new String[] {
			// Heavy images
			"https://octodex.github.com/images/hanukkat.png",
			"https://octodex.github.com/images/welcometocat.png",
			"https://octodex.github.com/images/filmtocat.png",
			"https://octodex.github.com/images/privateinvestocat.jpg",
			"https://octodex.github.com/images/gracehoppertocat.jpg",
			"https://octodex.github.com/images/minertocat.png",
			"https://octodex.github.com/images/jetpacktocat.png",
			"https://octodex.github.com/images/mountietocat.png",
			"https://octodex.github.com/images/saritocat.png",
			"https://octodex.github.com/images/octobiwan.jpg",
			"https://octodex.github.com/images/class-act.png",
			"https://octodex.github.com/images/benevocats.png",
			"https://octodex.github.com/images/drupalcat.jpg",
			"https://octodex.github.com/images/pythocat.png",
			"https://octodex.github.com/images/founding-father.jpg",
			"https://octodex.github.com/images/trekkie.png",
			"https://octodex.github.com/images/wilson.jpg",
			"https://octodex.github.com/images/ironcat.jpg",
			"https://octodex.github.com/images/inspectocat.jpg",
			"https://octodex.github.com/images/okal-eltocat.jpg",
			"https://octodex.github.com/images/supportcat.png",
			"https://octodex.github.com/images/cherryontop-o-cat.png",
			"https://octodex.github.com/images/xtocat.jpg",
			"https://octodex.github.com/images/bear-cavalry.png",
			"https://octodex.github.com/images/octocat-de-los-muertos.jpg",





			// Special cases
			"http://cdn.urbanislandz.com/wp-content/uploads/2011/10/MMSposter-large.jpg", // Very large image
			"http://www.ioncannon.net/wp-content/uploads/2011/06/test9.webp", // WebP image
			"http://4.bp.blogspot.com/-LEvwF87bbyU/Uicaskm-g6I/AAAAAAAAZ2c/V-WZZAvFg5I/s800/Pesto+Guacamole+500w+0268.jpg", // Image with "Mark has been invalidated" problem
			"file:///sdcard/Universal Image Loader @#&=+-_.,!()~'%20.png", // Image from SD card with encoded symbols
			"assets://Living Things @#&=+-_.,!()~'%20.jpg", // Image from assets
			"http://upload.wikimedia.org/wikipedia/ru/b/b6/Как_кот_с_мышами_воевал.png", // Link with UTF-8
			"https://www.eff.org/sites/default/files/chrome150_0.jpg", // Image from HTTPS
			"http://bit.ly/soBiXr", // Redirect link
			"http://img001.us.expono.com/100001/100001-1bc30-2d736f_m.jpg", // EXIF
			"", // Empty link
			"http://wrong.site.com/corruptedLink", // Wrong link
	};

	private Constants() {
	}

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}
	
	public static class Extra {
		public static final String FRAGMENT_INDEX = "com.nostra13.example.universalimageloader.FRAGMENT_INDEX";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
		public static final String IMAGE_LIST = "com.example.santa.myapplication.IMAGE_LIST";
	}

	public static int ITEM_IMAGE_MAXNUM = 3;
}
