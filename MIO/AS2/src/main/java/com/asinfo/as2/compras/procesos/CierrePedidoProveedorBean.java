/*   1:    */ package com.asinfo.as2.compras.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.DetallePedidoProveedor;
/*   7:    */ import com.asinfo.as2.entities.PedidoProveedor;
/*   8:    */ import com.asinfo.as2.entities.RegistroPeso;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*  11:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class CierrePedidoProveedorBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   protected transient ServicioRegistroPeso servicioRegistroPeso;
/*  33:    */   @EJB
/*  34:    */   protected transient ServicioGenerico<DetallePedidoProveedor> servicioDetallePedidoProveedor;
/*  35:    */   @EJB
/*  36:    */   protected transient ServicioPedidoProveedor servicioPedidoProveedor;
/*  37:    */   private LazyDataModel<DetallePedidoProveedor> listaDetallePedidoProveedor;
/*  38:    */   private List<DetallePedidoProveedor> listaDetallePedidoProveedorSeleccionado;
/*  39: 53 */   private String order = null;
/*  40:    */   protected RegistroPeso registroPeso;
/*  41:    */   private DetallePedidoProveedor detallePedidoProveedor;
/*  42:    */   private DataTable dtDetallePedido;
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 60 */     this.listaDetallePedidoProveedor = new LazyDataModel()
/*  48:    */     {
/*  49:    */       private static final long serialVersionUID = 1L;
/*  50:    */       
/*  51:    */       public List<DetallePedidoProveedor> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */       {
/*  53: 67 */         List<DetallePedidoProveedor> lista = new ArrayList();
/*  54:    */         
/*  55: 69 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  56: 70 */         if (filters.size() == 0)
/*  57:    */         {
/*  58: 72 */           filters.put("AND~01~01~pedidoProveedor.estado", "!=" + Estado.CERRADO.toString());
/*  59: 73 */           filters.put("AND~01~02~pedidoProveedor.estado", "!=" + Estado.ANULADO.toString());
/*  60:    */         }
/*  61: 76 */         filters = CierrePedidoProveedorBean.this.agregarFiltroPorTipoVisualizacionUsuario(filters, AppUtil.getUsuarioEnSesion(), true);
/*  62:    */         try
/*  63:    */         {
/*  64: 78 */           lista = CierrePedidoProveedorBean.this.servicioPedidoProveedor.obtenerListaPorPaginaDetallePedidoProveedor(startIndex, pageSize, sortField, ordenar, filters);
/*  65:    */         }
/*  66:    */         catch (Exception e)
/*  67:    */         {
/*  68: 80 */           e.printStackTrace();
/*  69:    */         }
/*  70: 82 */         CierrePedidoProveedorBean.this.listaDetallePedidoProveedor.setRowCount(CierrePedidoProveedorBean.this.servicioPedidoProveedor.contarPorCriterioDetallePedidoProveedor(filters));
/*  71: 83 */         return lista;
/*  72:    */       }
/*  73:    */     };
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void cargarListaProductosPedido()
/*  77:    */   {
/*  78: 89 */     this.listaDetallePedidoProveedorSeleccionado = new ArrayList();
/*  79: 90 */     DetallePedidoProveedor detallePedidoProveedor = (DetallePedidoProveedor)getDtDetallePedido().getRowData();
/*  80: 91 */     List<String> listaCampos = new ArrayList();
/*  81: 92 */     listaCampos.add("pedidoProveedor");
/*  82: 93 */     listaCampos.add("producto");
/*  83: 94 */     Map<String, String> filtros = new HashMap();
/*  84: 95 */     filtros.put("pedidoProveedor.idPedidoProveedor", detallePedidoProveedor.getPedidoProveedor().getIdPedidoProveedor() + "");
/*  85: 96 */     this.listaDetallePedidoProveedorSeleccionado = this.servicioDetallePedidoProveedor.obtenerListaPorPagina(DetallePedidoProveedor.class, 0, 9999, this.order, true, filtros, listaCampos);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void cerrarPedidoProveedor()
/*  89:    */   {
/*  90:104 */     int pedidoProveedor = ((DetallePedidoProveedor)this.listaDetallePedidoProveedorSeleccionado.get(0)).getPedidoProveedor().getId();
/*  91:105 */     this.servicioPedidoProveedor.actualizarEstado(Integer.valueOf(pedidoProveedor), Estado.CERRADO);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String editar()
/*  95:    */   {
/*  96:110 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  97:111 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String guardar()
/* 101:    */   {
/* 102:116 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/* 103:117 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String eliminar()
/* 107:    */   {
/* 108:122 */     return null;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String limpiar()
/* 112:    */   {
/* 113:127 */     return null;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String cargarDatos()
/* 117:    */   {
/* 118:132 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public RegistroPeso getRegistroPeso()
/* 122:    */   {
/* 123:136 */     return this.registroPeso;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setRegistroPeso(RegistroPeso registroPeso)
/* 127:    */   {
/* 128:140 */     this.registroPeso = registroPeso;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public LazyDataModel<DetallePedidoProveedor> getListaDetallePedidoProveedor()
/* 132:    */   {
/* 133:144 */     return this.listaDetallePedidoProveedor;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setListaDetallePedidoProveedor(LazyDataModel<DetallePedidoProveedor> listaDetallePedidoProveedor)
/* 137:    */   {
/* 138:148 */     this.listaDetallePedidoProveedor = listaDetallePedidoProveedor;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getOrder()
/* 142:    */   {
/* 143:152 */     return this.order;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setOrder(String order)
/* 147:    */   {
/* 148:156 */     this.order = order;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public DataTable getDtDetallePedido()
/* 152:    */   {
/* 153:160 */     return this.dtDetallePedido;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setDtDetallePedido(DataTable dtDetallePedido)
/* 157:    */   {
/* 158:164 */     this.dtDetallePedido = dtDetallePedido;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public List<DetallePedidoProveedor> getListaDetallePedidoProveedorSeleccionado()
/* 162:    */   {
/* 163:168 */     return this.listaDetallePedidoProveedorSeleccionado;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setListaDetallePedidoProveedorSeleccionado(List<DetallePedidoProveedor> listaDetallePedidoProveedorSeleccionado)
/* 167:    */   {
/* 168:172 */     this.listaDetallePedidoProveedorSeleccionado = listaDetallePedidoProveedorSeleccionado;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public DetallePedidoProveedor getDetallePedidoProveedor()
/* 172:    */   {
/* 173:176 */     return this.detallePedidoProveedor;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setDetallePedidoProveedor(DetallePedidoProveedor detallePedidoProveedor)
/* 177:    */   {
/* 178:180 */     this.detallePedidoProveedor = detallePedidoProveedor;
/* 179:    */   }
/* 180:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.CierrePedidoProveedorBean
 * JD-Core Version:    0.7.0.1
 */