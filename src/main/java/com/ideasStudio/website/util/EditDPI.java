package com.ideasStudio.website.util;

import java.io.File;
import java.io.IOException;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

public class EditDPI {
	public static void main(String[] args) throws ImageReadException, IOException {
		File imageFile = new File("C:/backups/201903280846289502402/1.png");
	      ImageInfo imageInfo = Sanselan.getImageInfo(imageFile);
	      System.out.println(imageInfo.getWidth());
	      System.out.println(imageInfo.getFormatName());
	      System.out.println(imageInfo.getMimeType());
	      System.out.println(imageInfo.getPhysicalHeightDpi());
	      System.out.println(imageInfo.getPhysicalWidthDpi());
	      System.out.println(imageInfo.getBitsPerPixel());
	      System.out.println(imageInfo.getFormatName());
	      System.out.println(imageInfo.getFormat());
	}
}
