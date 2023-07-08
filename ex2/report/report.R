report <- read.csv("C:\\Users\\Lo\\Desktop\\Cose mie\\Università\\Informatica\\2023 Q3\\Algoritmi\\laboratorio-algoritmi-2022-2023\\ex2\\report\\report_data.csv")
plot(report$max_height, log(report$loading_time), xaxt="n", pch = 20, ylab = "loading_time", xlab = "max_height")
axis(1, at = seq(1, 50, by = 1), las = 2)

report2 <- report[report$max_height >= 10, ]
plot(report2$max_height, report2$loading_time, xaxt="n", pch = 20, ylab = "loading_time", xlab = "max_height")
axis(1, at = seq(10, 50, by = 1), las = 2)
