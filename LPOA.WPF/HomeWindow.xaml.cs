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
    /// HomeWindow.xaml 的交互逻辑
    /// </summary>
    public partial class HomeWindow : Window
    {
        public HomeWindow()
        {
            InitializeComponent();
        }

        private void addMenuItem_Click(object sender, RoutedEventArgs e)
        {
            NewLeaveWindow win = new NewLeaveWindow();
            win.Show();
            
        }

        private void myMenuItem_Click(object sender, RoutedEventArgs e)
        {
            MyLeaveListWindow win = new MyLeaveListWindow();
            win.Show();
        }

        private void auditMenuItem_Click(object sender, RoutedEventArgs e)
        {
            LeaveAuditListWindow win = new LeaveAuditListWindow();
            win.Show();
        }
    }
}
