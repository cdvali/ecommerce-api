package com.vtech.ecommerce.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final S3Client s3Client;

	@Value("${aws.s3.bucket-name}")
	private String bucketName;

	public String uploadFile(String fileName, InputStream fileStream, long fileSize) {
		String key = generateUniqueKey(fileName);

		PutObjectRequest putRequest = PutObjectRequest.builder().bucket(bucketName).key(key).contentLength(fileSize)
				.build();

		s3Client.putObject(putRequest, RequestBody.fromInputStream(fileStream, fileSize));

		return "s3://" + bucketName + "/" + key;
	}

	public InputStream downloadFile(String key) {
		GetObjectRequest getRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();

		return s3Client.getObject(getRequest, ResponseTransformer.toInputStream());
	}

	private String generateUniqueKey(String fileName) {
		String timestamp = String.valueOf(Instant.now().toEpochMilli());
		return timestamp + "_" + fileName;
	}
}
