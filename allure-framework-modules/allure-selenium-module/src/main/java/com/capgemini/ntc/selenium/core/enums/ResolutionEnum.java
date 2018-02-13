package com.capgemini.ntc.selenium.core.enums;

public enum ResolutionEnum implements IResolutionList {
	// iOS
	iPhoneX(375, 812),
	iPhone8Plus(414, 736),
	iPhone8(375, 667),
	iPhone7Plus(414, 736),
	iPhone7(375, 667),
	iPhone6PlusAnd6SPlus(414, 736),
	iPhone6And6S(375, 667),
	iPHone5(320, 568),
	iPodTouch(320, 568),
	iPadPro(1024, 1366),
	iPad3rdAnd4th(768, 1024),
	iPadAir1And2(768, 1024),
	iPadMini(768, 1024),
	iPadMini2And3(768, 1024),
	
	// Android
	Nexus6P(411, 731),
	Nexus5X(411, 731),
	GooglePixel(411, 731),
	GooglePixelXL(411, 731),
	GooglePixel2(411, 731),
	GooglePixel2XL(411, 731),
	SamsungGalaxyNote5(480, 853),
	LGG5(480, 853),
	OnePlus3(480, 853),
	SamsungGalaxyS8(360, 740),
	SamsungGalaxyS8Plus(360, 740),
	SamsungGalaxyS7(360, 640),
	SamsungGalaxyS7Edge(360, 640),
	Nexus7(600, 960),
	Nexus9(768, 1024),
	SamsungGalaxyTab10(800, 1280),
	ChromebookPixel(1280, 850),
	
	// Desktop
	w320(320, 240),
	w480(480, 320),
	w568(568, 320),
	w768(768, 576),
	w960(960, 720),
	w1024(1024, 768),
	w1140(1140, 760),
	w1280(1280, 1024),
	w1366(1366, 768),
	w1600(1600, 1200),
	w1800(1800, 1440),
	w1920(1920, 1080);
	
	private int	width;
	private int	height;
	
	private ResolutionEnum(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String toString() {
		return "Widht:" + getWidth() + " Height:" + getHeight();
		
	}
}
