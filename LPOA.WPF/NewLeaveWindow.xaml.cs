using LPOA.Entity;
using LPOA.Sqlite;
using LPOA.WF;
using LPOA.WF.Leaves;
using LPOA.WF.Model;
using System;
using System.Activities;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace LPOA.WPF
{
    /// <summary>
    /// MainWindow.xaml 的交互逻辑
    /// </summary>
    public partial class NewLeaveWindow : Window
    {
        public NewLeaveWindow()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            LeavesDAL leaveDAL = new LeavesDAL();


            //NorthwindContext context = new NorthwindContext();
            //LeaveContext context = new LeaveContext();
            //var empList = context.Leaves.OrderBy(c => c.Id).ToList();

            LeaveEntity entity = new LeaveEntity();
            Guid leaveId = Guid.NewGuid();
            entity.Title = leaveId.ToString();
            string start = dpStart.Text;
            if (string.IsNullOrEmpty(start) == false) entity.StartDate = DateTime.Parse(start);
            string end = dpEnd.Text;
            if (string.IsNullOrEmpty(end) == false) entity.EndDate = DateTime.Parse(end);

            entity.Bumen = txtBumenzhuguan.Text;
            entity.Intro = txtIntro.Text;
            entity.LeaveType = cbType.Text;
            entity.LeaveUser = txtUserName.Text;
            entity.Shangji = txtShangji.Text;
            entity.Zongjingli = txtManager.Text;
            entity.State = 0;

            leaveDAL.ExecuteInsert(entity);

            System.Diagnostics.Debug.WriteLine("提交数据成功");
            System.Diagnostics.Debug.WriteLine("启动流程");

            log4net.ILog log = log4net.LogManager.GetLogger("mylog");
            if (log.IsDebugEnabled)
            {
                log.Debug("提交数据成功,启动流程");
            }

            //leaveContext.AddEntity(new WorkFlowEntity { FlowId = Guid.NewGuid().ToString(), WorkId = leaveId.ToString(), FlowLevel = 0, Intro = "提交请假表单", WorkType = "LEAVE" });
           
            //NorthwindContext<WorkFlow> flowContext = new NorthwindContext<WorkFlow>();

           
            //var list = flowContext.Context.OrderBy(c => c.Id).ToList();

            //wfc.AddEntity(new WorkFlow { Id = Guid.NewGuid(), WorkId = leaveId.ToString(), FlowLevel = 0, Intro = "提交请假表单", WorkType = "LEAVE" });

            LeaveActivity la = new LeaveActivity();

            WFWorker worker = new WFWorker();
            worker.WFName = "LEAVE";
            worker.WorkId = leaveId;
            worker.FlowLevel =0;
            //la.Worker = worker;
            IDictionary<string, object> dict = new Dictionary<string, object>();
            dict.Add("Worker", worker);

            var result = WorkflowInvoker.Invoke(la,dict);


            //启动工作流中的表单提交节点
            //IDictionary<string ,object> dc=new Dictionary<string,object>();
            //dc.Add("WorkId", leaveId);
            //dc.Add("WorkType", "LEAVE");
            //Guid workFlowId = WorkflowInvoker.Invoke(new FormSubmitActivity(),dc);

            //更新工作流ID到业务
            //leaveDAL.UpdateWorkFlowId(leaveId, workFlowId);

            MessageBox.Show("申请成功！等待审核！" );
            this.Close();
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            log4net.ILog log = log4net.LogManager.GetLogger("mylog");
            if (log.IsDebugEnabled)
            {
                log.Debug("提交数据成功,启动流程");
            }

            LeaveAuditListWindow frm = new LeaveAuditListWindow();
            frm.Show();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            dpStart.Text = DateTime.Now.ToShortDateString();
            dpEnd.Text = DateTime.Now.AddDays(3).ToShortDateString();
        }
    }
}
