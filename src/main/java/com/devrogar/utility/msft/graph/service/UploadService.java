package com.devrogar.utility.msft.graph.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devrogar.utility.config.SystemProperties;
import com.devrogar.utility.enums.PropertyKey;
import com.microsoft.graph.concurrency.ChunkedUploadProvider;
import com.microsoft.graph.concurrency.IProgressCallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.models.extensions.DriveItem;
import com.microsoft.graph.models.extensions.DriveItemUploadableProperties;
import com.microsoft.graph.models.extensions.UploadSession;

/**
 * 
 * @author Rohit Garlapati
 *
 */
@Service
public class UploadService {

	private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

	@Autowired
	private GraphService graphService;

	@Autowired
	private SystemProperties properties;

	public boolean uploadFile(String pathToFile) {
		File file = new File(pathToFile);
		try (InputStream fis = new FileInputStream(file)) {
			int fsize = fis.available();
			String fileName = file.getName();

			IProgressCallback<DriveItem> callback = new IProgressCallback<DriveItem> () {
				@Override
				public void progress(final long current, final long max) {
					logger.info("Progress current: {}, max: {}", current, max);
				}
				@Override
				public void success(final DriveItem result) {
					logger.info("File upload success, id: {}",result.id);
				}

				@Override
				public void failure(final ClientException ex) {
					logger.error("Error while uploading file, {}", ex.getMessage());
				}
			};

			UploadSession uploadSession = graphService
					.getGraphClient()
					.me()
					.drive()
					.items(properties.get(PropertyKey.ITEM_ID))
					.itemWithPath(fileName)
					.createUploadSession(new DriveItemUploadableProperties())
					.buildRequest()
					.post();

			ChunkedUploadProvider<DriveItem> chunkedUploadProvider = new ChunkedUploadProvider<DriveItem>(
					uploadSession, 
					graphService.getGraphClient(), 
					fis, 
					fsize, 
					DriveItem.class);
			
			chunkedUploadProvider.upload(callback);

		} catch (Exception e) {
			logger.error("Error while uploading file, {}", e.getMessage());
			return false;
		}
		return true;
	}

}
