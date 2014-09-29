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
using System.Collections.ObjectModel;
using LPOA.Entity;
using LPOA.Sqlite;


namespace LPOA.WPF
{
    /// <summary>
    /// LeaveWordFrm.xaml 的交互逻辑
    /// </summary>
    public partial class LeaveAuditListWindow : Window
    {
        public LeaveAuditListWindow()
        {
            InitializeComponent();
        }
        string LoginUser = "andy";

        ObservableCollection<LeaveEntity> ls;
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            #region 第一次审核并且上级是andy
            lblUser.Content = lblUser.Content + LoginUser;
            ls = new ObservableCollection<LeaveEntity>();

            LeavesDAL dal = new LeavesDAL();
            var list = dal.ExecuteListByShangji(LoginUser);//context.Leaves.Where(c => c.State == 0).Where(c => c.Shangji == LoginUser).OrderBy(c => c.Id).ToList();
            foreach (var item in list)
            {
                ls.Add(item);
            }

            //for (int i = 0; i < 10; i++)
            //{
            //    ls.Add(new Model.Leave() { Title = "t" + i, LeaveType = "事假" + i, LeaveUser = "AA" + i, StartDate = DateTime.Now });

            //}

            dgLeaves.ItemsSource = ls; 
            #endregion
        }

 

        private void DataGrid_PreviewMouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            var obj = sender as DataGrid;
            LeaveEntity le = obj.SelectedItem as LeaveEntity;
            LeaveDetailWindow win = new LeaveDetailWindow();
            win.LeaveId = le.Id;
            win.LoginUser = LoginUser;
            win.ShowDialog();
            //MessageBox.Show(le.Title);
        }

       
    }
}
