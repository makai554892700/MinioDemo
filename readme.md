#### 官方[网站](https://docs.min.io)/官方[文档](https://docs.min.io/cn/) / 个人 [demo](https://github.com/makai554892700/MinioDemo)
* minio部署
    * docker (对外公开9000端口，实例名为minio，挂载本地/data目录)

          docker run --name minio -v /data:/data -p 9000:9000 \
          -e "MINIO_ROOT_USER=AKIAIOSFODNN7EXAMPLE" \
          -e "MINIO_ROOT_PASSWORD=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY" \
          minio/minio server  /data

    * 其他方式参考官方[文档](https://docs.min.io/cn/)
* minio使用
    * 引用(gradle)

          dependencies {
            implementation 'io.minio:minio:3.0.10'
          }

    * 上传文件

          # 获取连接
          MinioClient minioClient = new MinioClient(host, accessKey, secretKey);
          # 判断存储桶是否存在
          boolean isExist = minioClient.bucketExists(bucketName);
          if (!isExist) {
            # 不存在时创建存储桶
            minioClient.makeBucket(bucketName);
          }
          # 上传文件到存储桶
          minioClient.putObject(bucketName, file.getOriginalFilename(), file.getInputStream()
                      , file.getContentType());

    * 需要下载文件时可能需要用到文件信息同时用到文件流，而minio这里由于是公开库，所以是分开处理的，需要调用2个api才行。
    * 获取文件信息(包含文件名/文件大小/文件类型等信息)

          minioClient.statObject(bucketName, objectName);

    * 获取文件流(用于下载)

          minioClient.getObject(bucketName, objectName);

    * 基础的使用就到这了，代码可以直接参考完整[demo](https://github.com/makai554892700/MinioDemo)






