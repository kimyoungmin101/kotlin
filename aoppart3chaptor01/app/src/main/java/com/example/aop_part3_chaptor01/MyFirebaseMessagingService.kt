package com.example.aop_part3_chaptor01

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

open class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        val type = remoteMessage.data["type"]?.let {
            NotificationType.valueOf(it)
        }
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        type ?: return


        NotificationManagerCompat.from(this).notify(type.id, createNotification(type, title, message))
    }

    private fun createNotificationChannel() { // channel 생성하는 과정 !!~!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun createNotification(
        type: NotificationType,
        title: String?,
        message: String?
    ): Notification {
        val intent = Intent(this,MainActivity::class.java).apply{
            putExtra("NotificationType", "${type.title} 타입")
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            // Stack 구조에서 TOP에는 Single 한개만 있는 것 화면이 한개 밖에 없음
            // B에서 B화면 으로 이동하는 경우 기존화면을 갱신하면 메모리 소모가 필요하고 같은 Activity가 Stack에 쌓이므로 SINGLE_TOP으로 플래그 생성 !
        }

        val pendingIntent = PendingIntent.getActivity(this,type.id,intent,FLAG_UPDATE_CURRENT)
        // pendingIntent란 ? 누군가한테 인텐트를 다룰 수 있는 권한을 준다고 생각!

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // 클릭했을때 자동으로 Notification이 닫힌다.

        when (type) {
            NotificationType.NORMAL -> Unit
            NotificationType.EXPENDABLE -> {
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(
                            "😁😒😊☺️😘😭😭☺️😳" +
                                    "😔😉😉😌😉😌🙈🙈👀" +
                                    "🙈👀😜😑😜😑😜😜😋😄😄" +
                                    "😔😉😉😌😉😌🙈🙈👀" +
                                    "🙈👀😜😑😜😑😜😜😋😄😄" +
                                    "🙈👀😜😑😜😑😜😜😋😄😄"
                        )
                )
            }
            NotificationType.CUSTOM -> {
                notificationBuilder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(
                        RemoteViews(
                            packageName,
                            R.layout.view_custom_notification
                        ).apply {
                            setTextViewText(R.id.title,title)
                            setTextViewText(R.id.message, message)
                        }
                    )
            }
        }

        return notificationBuilder.build()
    }

    companion object {
        private const val CHANNEL_NAME = "Emogi Party"
        private const val CHANNEL_DESCRIPTION = "Emogi Party를 위한 채널"
        private const val CHANNEL_ID = "Channel Id"
    }
}