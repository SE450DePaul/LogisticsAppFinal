package logistics.reportservice;

public final class ReportService implements Reporter {


    private volatile static ReportService instance;
    private Reporter reporter;

    private ReportService() {

    }

    public static ReportService getInstance()
    {
        if (instance == null)
        {
            synchronized (ReportService.class)
            {
                if (instance == null)
                {
                    instance = new ReportService();
                }
            }
        }
        return instance;
    }


    private void setReportType(String type){
       reporter = ReporterFactory.build(type);
    }


    @Override
    public void printOutput() {
        setReportType("facility");
        reporter.printOutput();
        setReportType("orders");
        reporter.printOutput();
        setReportType("facility");
        reporter.printOutput();
    }







}
