package net.joostory.jpastudy.repository

import net.joostory.jpastudy.domain.Item
import org.hibernate.annotations.QueryHints.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType
import javax.persistence.QueryHint

@Repository
interface ItemRepository: JpaRepository<Item, Long>
