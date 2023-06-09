package com.capstone.jeconn.data.dummy

import com.capstone.jeconn.data.entities.DetailInformation
import com.capstone.jeconn.data.entities.Image
import com.capstone.jeconn.data.entities.InvoiceEntity
import com.capstone.jeconn.data.entities.JobInformation
import com.capstone.jeconn.data.entities.LocationEntity
import com.capstone.jeconn.data.entities.Message
import com.capstone.jeconn.data.entities.MessageRoomEntity
import com.capstone.jeconn.data.entities.Notification
import com.capstone.jeconn.data.entities.NotificationData
import com.capstone.jeconn.data.entities.PrivateDataEntity
import com.capstone.jeconn.data.entities.PublicDataEntity
import com.capstone.jeconn.data.entities.VacanciesEntity


object DummyData {
    const val UID = "UID_USER_001"

    // PublicDataEntity dummy data
    val publicData: Map<String, PublicDataEntity> = mapOf(
        "john_doe66" to PublicDataEntity(
            username = "john_doe66",
            full_name = "John Doe",
            profile_image_url = "https://images.unsplash.com/photo-1618673747378-7e0d3561371a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8MTF8fHxlbnwwfHx8fHw%3D&w=1000&q=80",
            detail_information = DetailInformation(
                about_me = "I'm a software developer",
                date_of_birth = 946684800000L, // January 1, 2000
                gender = "Male"
            ),
            jobInformation = JobInformation(
                categories = listOf(1, 3),
                imagesUrl = mapOf(
                    "978307200000" to Image(
                        post_image_uid = "978307200000",
                        post_image_url = "https://media.istockphoto.com/id/1208307830/photo/musician-guy-in-hat-and-sunglasses-leaning-on-wall-playing-guitar-laughing-cheerful.jpg?s=612x612&w=0&k=20&c=xhpzZz4uIVaVt24u0G3iP8UOpbt71O1w0s8fIE_Y-YU=",
                    ),
                    "78307200000" to Image(
                        post_image_uid = "978307200000",
                        post_image_url = "https://images.pexels.com/photos/325688/pexels-photo-325688.jpeg?cs=srgb&dl=pexels-rene-asmussen-325688.jpg&fm=jpg",
                    )
                ),
                isOpenToOffer = true,
                location = LocationEntity(37.7749, -122.4194) // San Francisco location
            ),
            vacanciesPostId = mapOf(
                "1" to 1,
                "2" to 2
            ),
            notifications = listOf(
                Notification(System.currentTimeMillis(), "New Message", "You have a new message"),
                Notification(
                    System.currentTimeMillis(),
                    "Payment Reminder",
                    "Please make the payment"
                )
            ),
            messages_room_id = mapOf(
                "1" to 1,
                "2" to 2
            ),
            invoice_id = mapOf(
                "1" to 1,
                "2" to 2
            )
        ),
        "jane_smith66" to PublicDataEntity(
            username = "jane_smith66",
            full_name = "Jane Smith",
            profile_image_url = "https://media.istockphoto.com/id/1155368162/photo/beautiful-young-hipster-woman-with-curly-hair-with-red-guitar-in-neon-lights-rock-musician-is.jpg?s=612x612&w=0&k=20&c=L3vg8tdJUd_-aO0rF2C4ih_s2k3McWb1O7APjtNrW9k=",
            detail_information = DetailInformation(
                about_me = "I'm a graphic designer",
                date_of_birth = 978307200000L, // January 1, 2001
                gender = "Female"
            ),
            jobInformation = JobInformation(
                categories = listOf(2, 4),
                imagesUrl = mapOf(
                    "978307200000" to Image(
                        post_image_uid = "978307200000",
                        post_image_url = "https://media.istockphoto.com/id/1208307830/photo/musician-guy-in-hat-and-sunglasses-leaning-on-wall-playing-guitar-laughing-cheerful.jpg?s=612x612&w=0&k=20&c=xhpzZz4uIVaVt24u0G3iP8UOpbt71O1w0s8fIE_Y-YU=",
                    ),
                    "978307200000" to Image(
                        post_image_uid = "978307200000",
                        post_image_url = "https://media.gettyimages.com/id/456722336/photo/musician-adam-smith-of-temples-performs-in-concert-at-stubbs-amphitheater-on-october-5-2014-in.jpg?s=612x612&w=gi&k=20&c=jAyjUcihl2rDq8zmg3V-pP_m-h8G7hC3e_H3Y3Y2SNI=",
                    )
                ),
                isOpenToOffer = false,
                location = null
            ),
            vacanciesPostId = mapOf(
                "1" to 1,
            ),
            notifications = listOf(
                Notification(
                    System.currentTimeMillis(),
                    "New Job Opportunity",
                    "New job opening available"
                )
            ),
            messages_room_id = mapOf(
                "3" to 3,
                "4" to 4
            ),
            invoice_id = mapOf(
                "3" to 3,
                "4" to 4
            )
        )
    )

    // PrivateDataEntity dummy data
    val privateData: Map<String, PrivateDataEntity> = mapOf(
        "UID_USER_001" to PrivateDataEntity(
            email = "john@example.com",
            username = "john_doe66",
            created_date = System.currentTimeMillis(),

            ),
        "UID_USER_002" to PrivateDataEntity(
            email = "jane@example.com",
            username = "jane_smith66",
            created_date = System.currentTimeMillis(),
        )
    )

    // MessageRoomEntity dummy data
    val messageRooms: Map<Int, MessageRoomEntity> = mapOf(
        1 to MessageRoomEntity(
            members_username = mapOf(
                "jane_smith66" to "jane_smith66",
                "john_doe66" to "john_doe66"
            ),
            messages = mapOf(
                System.currentTimeMillis().toString() to Message(
                    System.currentTimeMillis(),
                    username = "jane_smith66",
                    message = "Hello",
                    invoice_id = 1
                ),
                System.currentTimeMillis().toString() to Message(
                    System.currentTimeMillis(),
                    username = "john_doe66",
                    message = "John Doe",
                    invoice_id = 1
                )
            )
        ),
    )

    // VacanciesEntity dummy data
    val vacancies: Map<Int, VacanciesEntity> = mapOf(
        1 to VacanciesEntity(
            username = "john_doe66",
            timestamp = System.currentTimeMillis(),
            start_salary = 5000,
            end_salary = 10000,
            category = listOf(1,2),
            description = "We are hiring software developers. Join our team!",
            location = LocationEntity(37.7749, -122.4194) // San Francisco location
        ),
        2 to VacanciesEntity(
            username = "jane_smith66",
            timestamp = System.currentTimeMillis(),
            start_salary = 10000,
            end_salary = 100000,
            category = listOf(1,8),
            description = "Looking for experienced graphic designers. Apply now!",
            location = LocationEntity(40.7128, -74.0060) // New York location
        ),
        3 to VacanciesEntity(
            username = "jane_smith66",
            timestamp = System.currentTimeMillis(),
            start_salary = 20000,
            end_salary = 25000,
            category = listOf(2,5),
            description = "We need skilled photographers for a project. Contact us for details!",
            location = LocationEntity(51.5074, -0.1278) // London location
        )
    )

    // InvoiceEntity dummy data
    val invoices: Map<Int, InvoiceEntity> = mapOf(
        1 to InvoiceEntity(
            invoice_id = 1,
            freelancer_username = "jane_smith66",
            tenant_username = "john_doe66",
            status = 0,
            service = "Web Development",
            created_date = "2023-05-01",
            start_date = System.currentTimeMillis(),
            end_date = System.currentTimeMillis() + 86400000, // 1 day later
            price = 5000,
            description = "Build a website",
            note = "Payment due in 7 days"
        ),
        2 to InvoiceEntity(
            invoice_id = 2,
            freelancer_username = "user1",
            tenant_username = "user3",
            status = 1,
            service = "Mobile App Development",
            created_date = "2023-05-15",
            start_date = System.currentTimeMillis(),
            end_date = System.currentTimeMillis() + 86400000, // 1 day later
            price = 7000,
            description = "Develop an iOS app"
        ),
        3 to InvoiceEntity(
            invoice_id = 3,
            freelancer_username = "user2",
            tenant_username = "user3",
            status = 2,
            service = "Graphic Design",
            created_date = "2023-05-20",
            start_date = System.currentTimeMillis(),
            end_date = System.currentTimeMillis() + 86400000, // 1 day later
            price = 6000,
            description = "Design a logo"
        ),
        4 to InvoiceEntity(
            invoice_id = 4,
            freelancer_username = "user2",
            tenant_username = "user1",
            status = 3,
            service = "Photography",
            created_date = "2023-05-25",
            start_date = System.currentTimeMillis(),
            end_date = System.currentTimeMillis() + 86400000, // 1 day later
            price = 8000,
            description = "Take professional photos"
        )
    )



    val entertainmentCategories: Map<Int, String> = mapOf(
        1 to "Musician",
        2 to "Model",
        3 to "Actor",
        4 to "Comedian",
        5 to "Dancer",
        6 to "Magician",
        7 to "Singer",
        8 to "TV Presenter",
        9 to "Radio Host",
        10 to "DJ",
    )

    //Notification dummy data
    val notificationData: Map<Int, NotificationData> = mapOf(
        1 to NotificationData(
            id = 1,
            title = "Congratulations Successful Payment",
            timestamp = System.currentTimeMillis(),
            description = "Congratulations Payment on behalf of Bily Hakim Erlangga has been successful, please check your account",
            //detailDescription = "Congratulations Payment on behalf of Bily Hakim Erlangga has been successful, please check your account"
        ),
        2 to NotificationData(
            id = 2,
            title = "Congratulations Successful Payment",
            timestamp = System.currentTimeMillis(),
            description = "Congratulations Payment on behalf of Bily Hakim Erlangga has been successful, please check your account",
            //detailDescription = "Congratulations Payment on behalf of Bily Hakim Erlangga has been successful, please check your account"
        ),
        3 to NotificationData(
            id = 3,
            title = "Congratulations Successful Payment",
            timestamp = System.currentTimeMillis(),
            description = "3 Congratulations Payment on behalf of Bily Hakim Erlangga has been successful, please check your account",
            //detailDescription = "Congratulations Payment on behalf of Bily Hakim Erlangga has been successful, please check your account"
        ),
        4 to NotificationData(
            id = 4,
            title = "Congratulations Successful Payment",
            timestamp = System.currentTimeMillis(),
            description = "Congratulations Payment on behalf of Bily Hakim Erlangga has been successful, please check your account",
            //detailDescription = "Congratulations Payment on behalf of Bily Hakim Erlangga has been successful, please check your account"
        ),
        5 to NotificationData(
            id = 5,
            title = "Congratulations Successful Payment",
            timestamp = System.currentTimeMillis(),
            description = "Congratulations Payment on behalf of Bily Hakim Erlangga has been successful, please check your account",
            //detailDescription = "Congratulations Payment on behalf of Bily Hakim Erlangga has been successful, please check your account"
        )
    )
}