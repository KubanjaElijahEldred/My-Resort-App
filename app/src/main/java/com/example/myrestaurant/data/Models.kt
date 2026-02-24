package com.example.myrestaurant.data

import java.time.LocalDate
import java.time.LocalDateTime

data class Booking(
    val id: String,
    val guestName: String,
    val roomType: String,
    val roomNumber: String,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val totalAmount: Double,
    val status: BookingStatus,
    val guestCount: Int,
    val specialRequests: String = "",
    val contactNumber: String,
    val email: String
)

enum class BookingStatus {
    CONFIRMED,
    CHECKED_IN,
    CHECKED_OUT,
    CANCELLED,
    PENDING
}

data class Room(
    val id: String,
    val number: String,
    val type: String,
    val floor: Int,
    val pricePerNight: Double,
    val status: RoomStatus,
    val amenities: List<String>,
    val maxOccupancy: Int,
    val lastCleaned: LocalDateTime,
    val currentGuest: String? = null
)

enum class RoomStatus {
    AVAILABLE,
    OCCUPIED,
    MAINTENANCE,
    CLEANING,
    OUT_OF_ORDER
}

data class FinancialReport(
    val totalRevenue: Double,
    val monthlyRevenue: Double,
    val dailyRevenue: Double,
    val occupancyRate: Double,
    val totalBookings: Int,
    val cancelledBookings: Int,
    val averageBookingValue: Double,
    val revenueByRoomType: Map<String, Double>
)

data class Transaction(
    val id: String,
    val bookingId: String,
    val guestName: String,
    val amount: Double,
    val type: TransactionType,
    val timestamp: LocalDateTime,
    val paymentMethod: String,
    val status: TransactionStatus
)

enum class TransactionType {
    PAYMENT,
    REFUND,
    DEPOSIT
}

enum class TransactionStatus {
    COMPLETED,
    PENDING,
    FAILED,
    REFUNDED
}
