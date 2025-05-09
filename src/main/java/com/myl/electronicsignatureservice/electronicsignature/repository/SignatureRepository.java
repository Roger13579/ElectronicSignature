package com.myl.electronicsignatureservice.electronicsignature.repository;

import com.myl.electronicsignatureservice.electronicsignature.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureRepository extends JpaRepository<FileData,Long> {
}
