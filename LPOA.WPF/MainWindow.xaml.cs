using LPOA.Model;
using LPOA.WF;
using LPOA.WF.Leaves;
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
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
           
            


            NorthwindContext context = new NorthwindContext();
            //var empList = context.Leaves.OrderBy(c => c.Id).ToList();

            Leave entity = new Leave();
            entity.Title = "请假";
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


            context.AddEntity(entity);

            System.Diagnostics.Debug.WriteLine("提交数据成功");
            System.Diagnostics.Debug.WriteLine("启动流程");
            log4net.ILog log = log4net.LogManager.GetLogger("mylog");
            if (log.IsDebugEnabled)
            {
                log.Debug("提交数据成功,启动流程");
            }

            IDictionary<string ,object> dc=new Dictionary<string,object>();
            dc.Add("UserId", 10012);
            //启动工作流
            LeaveActivity la = new LeaveActivity();
            FormSubmitActivity fa = new WF.Leaves.FormSubmitActivity();
            fa.Text = "OKAAAA";
            
            int reuslt = WorkflowInvoker.Invoke(new FormSubmitActivity(),dc);

            MessageBox.Show("申请成功！等待审核！" + reuslt.ToString());
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            log4net.ILog log = log4net.LogManager.GetLogger("mylog");
            if (log.IsDebugEnabled)
            {
                log.Debug("提交数据成功,启动流程");
            }

            LeaveList frm = new LeaveList();
            frm.Show();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            dpStart.Text = DateTime.Now.ToShortDateString();
            dpEnd.Text = DateTime.Now.AddDays(3).ToShortDateString();
        }
    }
}
