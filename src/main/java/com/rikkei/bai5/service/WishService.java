package com.rikkei.bai5.service;

import com.rikkei.bai5.exception.InvalidWishException;
import com.rikkei.bai5.exception.WishLimitExceededException;
import com.rikkei.bai5.model.KnowledgeRequest;
import com.rikkei.bai5.model.TransformRequest;
import com.rikkei.bai5.model.WealthRequest;
import com.rikkei.bai5.model.WishHistory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WishService {

    private final AtomicInteger wishCount = new AtomicInteger(0);
    private final List<WishHistory> history = new ArrayList<>();
    private static final int MAX_WISHES = 3;

    public synchronized WishHistory grantWealthWish(WealthRequest request) {
        checkWishLimit();

        if (request.getAmount() == null || request.getAmount() <= 0) {
            recordHistory("WEALTH", "REJECTED", "Số tiền không hợp lệ.");
            throw new InvalidWishException("Số tiền không hợp lệ.");
        }
        if (request.getAmount() > 1_000_000_000) {
            recordHistory("WEALTH", "REJECTED", "Thần đèn không có nhiều tiền đến vậy!");
            throw new InvalidWishException("Thần đèn không có nhiều tiền đến vậy! Tối đa 1 tỷ thôi nhé.");
        }

        wishCount.incrementAndGet();
        return recordHistory("WEALTH", "GRANTED", "Thần đèn đã ban cho bạn " + request.getAmount() + " " + request.getCurrency());
    }

    public synchronized WishHistory grantKnowledgeWish(KnowledgeRequest request) {
        checkWishLimit();

        if (request.getTopic() == null || request.getTopic().trim().isEmpty()) {
            recordHistory("KNOWLEDGE", "REJECTED", "Chủ đề kiến thức không được để trống.");
            throw new InvalidWishException("Chủ đề kiến thức không được để trống.");
        }
        if (request.getTopic().toLowerCase().contains("tương lai")) {
            recordHistory("KNOWLEDGE", "REJECTED", "Thần đèn không thể nhìn thấu tương lai!");
            throw new InvalidWishException("Thần đèn không thể nhìn thấu tương lai!");
        }

        wishCount.incrementAndGet();
        return recordHistory("KNOWLEDGE", "GRANTED", "Bạn đã trở thành chuyên gia về " + request.getTopic());
    }

    public synchronized WishHistory grantTransformWish(TransformRequest request) {
        checkWishLimit();

        if (request.getItem() == null || request.getTarget() == null || request.getItem().trim().isEmpty() || request.getTarget().trim().isEmpty()) {
            recordHistory("TRANSFORM", "REJECTED", "Vật phẩm không hợp lệ.");
            throw new InvalidWishException("Bạn cần chỉ định rõ vật phẩm ban đầu và vật phẩm mục tiêu.");
        }
        if (request.getItem().equalsIgnoreCase(request.getTarget())) {
            recordHistory("TRANSFORM", "REJECTED", "Hai vật phẩm giống nhau.");
            throw new InvalidWishException("Vật phẩm ban đầu và mục tiêu giống nhau, không cần biến hóa.");
        }

        wishCount.incrementAndGet();
        return recordHistory("TRANSFORM", "GRANTED", "Úm ba la! " + request.getItem() + " đã biến thành " + request.getTarget());
    }

    public List<WishHistory> getHistory() {
        return new ArrayList<>(history);
    }

    private void checkWishLimit() {
        if (wishCount.get() >= MAX_WISHES) {
            recordHistory("UNKNOWN", "REJECTED", "Thần đèn đã hết lượt ước!");
            throw new WishLimitExceededException("Thần đèn đã hết lượt ước! Chỉ có tối đa 3 điều ước thôi nhé.");
        }
    }

    private WishHistory recordHistory(String wishType, String status, String message) {
        WishHistory record = new WishHistory(
                UUID.randomUUID().toString(),
                wishType,
                status,
                message,
                LocalDateTime.now()
        );
        history.add(record);
        return record;
    }
}
