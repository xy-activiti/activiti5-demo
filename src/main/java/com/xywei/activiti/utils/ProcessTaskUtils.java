package com.xywei.activiti.utils;

import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

public class ProcessTaskUtils {

	public static void completeTask(TaskService taskService, String taskId, Map<String, Object> variables) {

		taskService.complete(taskId, variables);

	}

	public List<Task> findTaskEntitiesByTaskAssignee(TaskService taskService, String assignee) {

		List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().desc().list();

		return tasks;

	}
}
