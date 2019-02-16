package com.sample.todo.ui.taskdetail

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sample.todo.R
import com.sample.todo.databinding.TaskDetailFragmentBinding
import com.sample.todo.ui.message.MessageManager
import com.sample.todo.ui.message.setUpSnackbar
import com.sample.todo.util.autoId
import com.sample.todo.util.extension.observeEvent
import com.sample.todo.util.setupWith
import dagger.android.support.DaggerFragment
import javax.inject.Inject

// TODO DI use navArg lazy by navArgs() see: https://issuetracker.google.com/issues/122603367
class TaskDetailFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var messageManager: MessageManager
    @Inject
    lateinit var notificationManager: NotificationManagerCompat
    private lateinit var binding: TaskDetailFragmentBinding
    private val taskDetailViewModel: TaskDetailViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskDetailFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = taskDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            bottomNavigationView.setupWith(this@TaskDetailFragment)
        }
        taskDetailViewModel.apply {
            navigateUpEvent.observeEvent(viewLifecycleOwner) {
                findNavController().navigateUp()
            }
            navigationEvent.observeEvent(viewLifecycleOwner) {
                findNavController().navigate(it)
            }
            addNotificationEvent.observeEvent(viewLifecycleOwner) {
                addEvent(it)
            }
        }
        setUpSnackbar(
            taskDetailViewModel.snackBarMessage,
            binding.snackBar,
            messageManager
        )
        return binding.root
    }

    /**
     * create notification that open task detail screen with taskId
     */
    private fun addEvent(taskId: String) {
        createNotificationChannel()
        val pendingIntent = findNavController().createDeepLink()
            .setGraph(R.navigation.tasks_graph)
            .setDestination(R.id.taskDetailFragment)
            .setArguments(
                TaskDetailFragmentArgs(taskId).toBundle()
            )
            .createPendingIntent()

        val notiBuilder = NotificationCompat.Builder(requireContext(), "111")
            .setSmallIcon(R.drawable.taskdetail_notification)
            .setContentTitle("Click here map go task detail")
            .setContentText("id: $taskId")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(autoId().hashCode(), notiBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "my channel"
            val descriptionText = "des"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("111", name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}