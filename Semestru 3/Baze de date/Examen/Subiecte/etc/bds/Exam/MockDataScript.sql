USE [StudentsGradesManagement]
GO
SET IDENTITY_INSERT [dbo].[Groups] ON 

INSERT [dbo].[Groups] ([id], [name]) VALUES (1, N'911  ')
INSERT [dbo].[Groups] ([id], [name]) VALUES (2, N'912  ')
INSERT [dbo].[Groups] ([id], [name]) VALUES (3, N'913  ')
SET IDENTITY_INSERT [dbo].[Groups] OFF
SET IDENTITY_INSERT [dbo].[Students] ON 

INSERT [dbo].[Students] ([id], [name], [group_id]) VALUES (1, N'Georgel        ', 1)
INSERT [dbo].[Students] ([id], [name], [group_id]) VALUES (2, N'Andrei         ', 1)
INSERT [dbo].[Students] ([id], [name], [group_id]) VALUES (3, N'Anca           ', 2)
INSERT [dbo].[Students] ([id], [name], [group_id]) VALUES (4, N'Vasile         ', 2)
INSERT [dbo].[Students] ([id], [name], [group_id]) VALUES (5, N'Adriana        ', 3)
SET IDENTITY_INSERT [dbo].[Students] OFF
SET IDENTITY_INSERT [dbo].[Tasks] ON 

INSERT [dbo].[Tasks] ([id], [name]) VALUES (1, N'lab1           ')
INSERT [dbo].[Tasks] ([id], [name]) VALUES (2, N'lab2           ')
INSERT [dbo].[Tasks] ([id], [name]) VALUES (3, N'lab3           ')
SET IDENTITY_INSERT [dbo].[Tasks] OFF
INSERT [dbo].[StudentTasks] ([student_id], [task_id], [grade]) VALUES (1, 1, 10)
INSERT [dbo].[StudentTasks] ([student_id], [task_id], [grade]) VALUES (1, 2, 9)
INSERT [dbo].[StudentTasks] ([student_id], [task_id], [grade]) VALUES (1, 3, 10)
INSERT [dbo].[StudentTasks] ([student_id], [task_id], [grade]) VALUES (3, 1, NULL)
INSERT [dbo].[StudentTasks] ([student_id], [task_id], [grade]) VALUES (3, 2, 8)
INSERT [dbo].[StudentTasks] ([student_id], [task_id], [grade]) VALUES (4, 1, 5)
INSERT [dbo].[StudentTasks] ([student_id], [task_id], [grade]) VALUES (4, 2, 7)
INSERT [dbo].[StudentTasks] ([student_id], [task_id], [grade]) VALUES (4, 3, 6)
INSERT [dbo].[StudentTasks] ([student_id], [task_id], [grade]) VALUES (5, 1, 10)
SET IDENTITY_INSERT [dbo].[Comments] ON 

INSERT [dbo].[Comments] ([id], [status]) VALUES (1, N'resolved  ')
INSERT [dbo].[Comments] ([id], [status]) VALUES (2, N'opened    ')
SET IDENTITY_INSERT [dbo].[Comments] OFF
INSERT [dbo].[CommentsGrades] ([student_id], [task_id], [status_id]) VALUES (1, 1, 1)
INSERT [dbo].[CommentsGrades] ([student_id], [task_id], [status_id]) VALUES (1, 2, 1)
INSERT [dbo].[CommentsGrades] ([student_id], [task_id], [status_id]) VALUES (1, 3, 1)
INSERT [dbo].[CommentsGrades] ([student_id], [task_id], [status_id]) VALUES (3, 2, 2)
INSERT [dbo].[CommentsGrades] ([student_id], [task_id], [status_id]) VALUES (4, 1, 1)
INSERT [dbo].[CommentsGrades] ([student_id], [task_id], [status_id]) VALUES (4, 2, 1)
INSERT [dbo].[CommentsGrades] ([student_id], [task_id], [status_id]) VALUES (4, 3, 2)
INSERT [dbo].[CommentsGrades] ([student_id], [task_id], [status_id]) VALUES (5, 1, 1)
INSERT [dbo].[CommentsGrades] ([student_id], [task_id], [status_id]) VALUES (5, 1, 2)
