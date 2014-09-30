
using LPOA.Sqlite;
using System;
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
using System.Windows.Shapes;
using LPOA.WF;
using LPOA.WF.Leaves;
using System.Activities;
using LPOA.Entity;
using LPOA.WF.Model;

namespace LPOA.WPF
{
    /// <summary>
    /// LeaveDetailWindow.xaml 的交互逻辑
    /// </summary>
    public partial class LeaveDetailWindow : Window
    {
        public long LeaveId { get; set; }
        public string LoginUser { get; set; }
        private Guid workId { get; set; }
        LeaveEntity leaveEntity;
        public LeaveDetailWindow()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            if (LeaveId > 0)
            {
                LeavesDAL leaveDAL = new LeavesDAL();
                leaveEntity = leaveDAL.ExecuteEntity(LeaveId);

                workId = Guid.Parse(leaveEntity.Title);
                dpStart.Text = leaveEntity.StartDate.ToShortDateString();
                dpEnd.Text = leaveEntity.EndDate.ToShortDateString();
                txtUserName.Text = leaveEntity.LeaveUser;
                txtBumenzhuguan.Text = leaveEntity.Bumen;
                txtIntro.Text = leaveEntity.Intro;
                txtManager.Text = leaveEntity.Zongjingli;
                txtShangji.Text = leaveEntity.Shangji;
                cbType.Text = leaveEntity.LeaveType;
                lblID.Content = leaveEntity.Id.ToString();
                if (LoginUser == leaveEntity.LeaveUser)
                {
                    btnAudit.IsEnabled = false;
                }
            }
        }

        private void btnAudit_Click(object sender, RoutedEventArgs e)
        {

            LeaveActivity la = new LeaveActivity();

            WFWorker worker = new WFWorker();
            worker.WFName = "LEAVE";
            worker.WorkId = workId;
            worker.Worker = LoginUser;
            worker.FlowLevel = 1;

            //la.Worker = worker;
            IDictionary<string, object> dict = new Dictionary<string, object>();
            dict.Add("Worker", worker);

            var result = WorkflowInvoker.Invoke(la, dict);



            ////启动工作流中的表单提交节点
            //IDictionary<string, object> dc = new Dictionary<string, object>();
            //dc.Add("FlowId", leaveEntity.WorkFlowId);
            //dc.Add("Worker", LoginUser);

            //SupervisorAuditActivity fa = new SupervisorAuditActivity();


            //WorkflowInvoker.Invoke(fa);
            MessageBox.Show("审批成功");
            this.Close();
        }
    }
}
