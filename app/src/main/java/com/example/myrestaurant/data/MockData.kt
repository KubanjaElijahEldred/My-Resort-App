package com.example.myrestaurant.data

import java.time.LocalDate
import java.time.LocalDateTime

object MockData {
    
    val bookings = listOf(
        Booking(
            id = "B001",
            guestName = "John Smith",
            roomType = "Grand Suite",
            roomNumber = "G101",
            checkInDate = LocalDate.of(2024, 2, 20),
            checkOutDate = LocalDate.of(2024, 2, 25),
            totalAmount = 1250.00,
            status = BookingStatus.CHECKED_IN,
            guestCount = 2,
            specialRequests = "Late check-in, Extra towels",
            contactNumber = "+256 785 123456",
            email = "john.smith@email.com"
        ),
        Booking(
            id = "B002",
            guestName = "Sarah Johnson",
            roomType = "Double Room",
            roomNumber = "D205",
            checkInDate = LocalDate.of(2024, 2, 18),
            checkOutDate = LocalDate.of(2024, 2, 22),
            totalAmount = 600.00,
            status = BookingStatus.CONFIRMED,
            guestCount = 2,
            specialRequests = "Vegetarian breakfast",
            contactNumber = "+256 782 987654",
            email = "sarah.j@email.com"
        ),
        Booking(
            id = "B003",
            guestName = "Michael Brown",
            roomType = "Single Room",
            roomNumber = "S301",
            checkInDate = LocalDate.of(2024, 2, 15),
            checkOutDate = LocalDate.of(2024, 2, 20),
            totalAmount = 375.00,
            status = BookingStatus.CHECKED_OUT,
            guestCount = 1,
            specialRequests = "Early check-out",
            contactNumber = "+256 777 456789",
            email = "michael.b@email.com"
        ),
        Booking(
            id = "B004",
            guestName = "Emma Davis",
            roomType = "Grand Suite",
            roomNumber = "G102",
            checkInDate = LocalDate.of(2024, 2, 22),
            checkOutDate = LocalDate.of(2024, 2, 28),
            totalAmount = 1750.00,
            status = BookingStatus.CONFIRMED,
            guestCount = 3,
            specialRequests = "Extra bed, Baby cot",
            contactNumber = "+256 703 234567",
            email = "emma.davis@email.com"
        ),
        Booking(
            id = "B005",
            guestName = "Robert Wilson",
            roomType = "Double Room",
            roomNumber = "D206",
            checkInDate = LocalDate.of(2024, 2, 19),
            checkOutDate = LocalDate.of(2024, 2, 21),
            totalAmount = 400.00,
            status = BookingStatus.CANCELLED,
            guestCount = 2,
            specialRequests = "",
            contactNumber = "+256 755 345678",
            email = "robert.w@email.com"
        )
    )
    
    val rooms = listOf(
        Room(
            id = "R001",
            number = "G101",
            type = "Grand Suite",
            floor = 1,
            pricePerNight = 250.00,
            status = RoomStatus.OCCUPIED,
            amenities = listOf("King Bed", "Ocean View", "Mini Bar", "Jacuzzi", "Balcony"),
            maxOccupancy = 4,
            lastCleaned = LocalDateTime.of(2024, 2, 20, 10, 0),
            currentGuest = "John Smith"
        ),
        Room(
            id = "R002",
            number = "G102",
            type = "Grand Suite",
            floor = 1,
            pricePerNight = 250.00,
            status = RoomStatus.AVAILABLE,
            amenities = listOf("King Bed", "Ocean View", "Mini Bar", "Jacuzzi", "Balcony"),
            maxOccupancy = 4,
            lastCleaned = LocalDateTime.of(2024, 2, 21, 14, 0)
        ),
        Room(
            id = "R003",
            number = "D205",
            type = "Double Room",
            floor = 2,
            pricePerNight = 150.00,
            status = RoomStatus.AVAILABLE,
            amenities = listOf("Queen Bed", "City View", "Mini Bar", "Work Desk"),
            maxOccupancy = 2,
            lastCleaned = LocalDateTime.of(2024, 2, 21, 12, 0)
        ),
        Room(
            id = "R004",
            number = "D206",
            type = "Double Room",
            floor = 2,
            pricePerNight = 150.00,
            status = RoomStatus.CLEANING,
            amenities = listOf("Queen Bed", "City View", "Mini Bar", "Work Desk"),
            maxOccupancy = 2,
            lastCleaned = LocalDateTime.of(2024, 2, 21, 15, 0)
        ),
        Room(
            id = "R005",
            number = "S301",
            type = "Single Room",
            floor = 3,
            pricePerNight = 75.00,
            status = RoomStatus.AVAILABLE,
            amenities = listOf("Single Bed", "Garden View", "Work Desk"),
            maxOccupancy = 1,
            lastCleaned = LocalDateTime.of(2024, 2, 20, 16, 0)
        ),
        Room(
            id = "R006",
            number = "S302",
            type = "Single Room",
            floor = 3,
            pricePerNight = 75.00,
            status = RoomStatus.MAINTENANCE,
            amenities = listOf("Single Bed", "Garden View", "Work Desk"),
            maxOccupancy = 1,
            lastCleaned = LocalDateTime.of(2024, 2, 19, 11, 0)
        )
    )
    
    val transactions = listOf(
        Transaction(
            id = "T001",
            bookingId = "B001",
            guestName = "John Smith",
            amount = 1250.00,
            type = TransactionType.PAYMENT,
            timestamp = LocalDateTime.of(2024, 2, 20, 14, 30),
            paymentMethod = "Credit Card",
            status = TransactionStatus.COMPLETED
        ),
        Transaction(
            id = "T002",
            bookingId = "B002",
            guestName = "Sarah Johnson",
            amount = 600.00,
            type = TransactionType.PAYMENT,
            timestamp = LocalDateTime.of(2024, 2, 18, 16, 45),
            paymentMethod = "Mobile Money",
            status = TransactionStatus.COMPLETED
        ),
        Transaction(
            id = "T003",
            bookingId = "B003",
            guestName = "Michael Brown",
            amount = 375.00,
            type = TransactionType.PAYMENT,
            timestamp = LocalDateTime.of(2024, 2, 15, 12, 0),
            paymentMethod = "Credit Card",
            status = TransactionStatus.COMPLETED
        ),
        Transaction(
            id = "T004",
            bookingId = "B004",
            guestName = "Emma Davis",
            amount = 875.00,
            type = TransactionType.DEPOSIT,
            timestamp = LocalDateTime.of(2024, 2, 22, 10, 15),
            paymentMethod = "Bank Transfer",
            status = TransactionStatus.COMPLETED
        )
    )
    
    fun getFinancialReport(): FinancialReport {
        val totalRevenue = transactions.sumOf { it.amount }
        val monthlyRevenue = transactions.filter { 
            it.timestamp.month == LocalDateTime.now().month 
        }.sumOf { it.amount }
        val dailyRevenue = transactions.filter { 
            it.timestamp.toLocalDate() == LocalDate.now() 
        }.sumOf { it.amount }
        
        val occupiedRooms = rooms.count { it.status == RoomStatus.OCCUPIED }
        val occupancyRate = (occupiedRooms.toDouble() / rooms.size) * 100
        
        val totalBookings = bookings.size
        val cancelledBookings = bookings.count { it.status == BookingStatus.CANCELLED }
        val averageBookingValue = if (totalBookings > 0) totalRevenue / totalBookings else 0.0
        
        val revenueByRoomType = bookings.groupBy { it.roomType }
            .mapValues { (_, bookingList) -> bookingList.sumOf { it.totalAmount } }
        
        return FinancialReport(
            totalRevenue = totalRevenue,
            monthlyRevenue = monthlyRevenue,
            dailyRevenue = dailyRevenue,
            occupancyRate = occupancyRate,
            totalBookings = totalBookings,
            cancelledBookings = cancelledBookings,
            averageBookingValue = averageBookingValue,
            revenueByRoomType = revenueByRoomType
        )
    }
}
