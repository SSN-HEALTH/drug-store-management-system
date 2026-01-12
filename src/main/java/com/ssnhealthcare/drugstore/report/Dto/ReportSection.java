package com.ssnhealthcare.drugstore.report.Dto;

import java.util.List;

public record ReportSection(String title, List<String> headers, List<List<String>> rows) {}
