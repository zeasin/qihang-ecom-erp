using LPOA.Model;
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

namespace LPOA.WPF
{
    /// <summary>
    /// LeaveList.xaml 的交互逻辑
    /// </summary>
    public partial class LeaveList : Window
    {
        public LeaveList()
        {
            InitializeComponent();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {

            NorthwindContext context = new NorthwindContext();
            var empList = context.GetById(2);
            if (empList.StartDate.HasValue)
                dpStart.Text = empList.StartDate.Value.ToShortDateString();
            if (empList.EndDate.HasValue) dpEnd.Text = empList.EndDate.Value.ToShortDateString();
            txtUserName.Text = empList.LeaveUser;
            txtBumenzhuguan.Text = empList.Bumen;
            txtIntro.Text = empList.Intro;
            txtManager.Text = empList.Zongjingli;
            txtShangji.Text = empList.Shangji;
            cbType.Text = empList.LeaveType;
            lblID.Content = empList.Id.ToString();
        }

        
    }
}
