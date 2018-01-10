/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.entities.TipoVehiculo;
/*   8:    */ import com.asinfo.as2.entities.Transportista;
/*   9:    */ import com.asinfo.as2.entities.Vehiculo;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTipoVehiculo;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioVehiculo;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ViewScoped;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class VehiculoBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = -8204372454439685260L;
/*  32:    */   @EJB
/*  33:    */   private ServicioVehiculo servicioVehiculo;
/*  34:    */   @EJB
/*  35:    */   private ServicioTransportista servicioTransportista;
/*  36:    */   @EJB
/*  37:    */   private ServicioTipoVehiculo servicioTipoVehiculo;
/*  38:    */   private Vehiculo vehiculo;
/*  39:    */   private LazyDataModel<Vehiculo> listaVehiculo;
/*  40:    */   private List<Vehiculo> listaVehiculoCombo;
/*  41:    */   private List<Transportista> listaTransportistaCombo;
/*  42:    */   private List<TipoVehiculo> listaTipoVehiculoCombo;
/*  43:    */   private DataTable dtVehiculo;
/*  44:    */   
/*  45:    */   @PostConstruct
/*  46:    */   public void init()
/*  47:    */   {
/*  48: 62 */     this.listaVehiculo = new LazyDataModel()
/*  49:    */     {
/*  50:    */       private static final long serialVersionUID = 1L;
/*  51:    */       
/*  52:    */       public List<Vehiculo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  53:    */       {
/*  54: 69 */         List<Vehiculo> lista = new ArrayList();
/*  55: 70 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  56:    */         
/*  57: 72 */         lista = VehiculoBean.this.servicioVehiculo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  58: 73 */         VehiculoBean.this.listaVehiculo.setRowCount(VehiculoBean.this.servicioVehiculo.contarPorCriterio(filters));
/*  59:    */         
/*  60: 75 */         return lista;
/*  61:    */       }
/*  62:    */     };
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String editar()
/*  66:    */   {
/*  67: 83 */     if (getVehiculo().getId() != 0) {
/*  68: 84 */       setEditado(true);
/*  69:    */     } else {
/*  70: 86 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  71:    */     }
/*  72: 88 */     return "";
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String guardar()
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79: 94 */       this.servicioVehiculo.guardar(getVehiculo());
/*  80: 95 */       limpiar();
/*  81: 96 */       setEditado(false);
/*  82: 97 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  83:    */     }
/*  84:    */     catch (Exception e)
/*  85:    */     {
/*  86: 99 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  87:100 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  88:    */     }
/*  89:102 */     return "";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String eliminar()
/*  93:    */   {
/*  94:    */     try
/*  95:    */     {
/*  96:108 */       this.servicioVehiculo.eliminar(this.vehiculo);
/*  97:109 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  98:110 */       limpiar();
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:112 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 103:113 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 104:    */     }
/* 105:116 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String limpiar()
/* 109:    */   {
/* 110:121 */     crearVehiculo();
/* 111:122 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String cargarDatos()
/* 115:    */   {
/* 116:127 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void crearVehiculo()
/* 120:    */   {
/* 121:131 */     this.vehiculo = new Vehiculo();
/* 122:132 */     this.vehiculo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 123:133 */     this.vehiculo.setIdSucursal(AppUtil.getSucursal().getId());
/* 124:134 */     this.vehiculo.setTransportista(new Transportista());
/* 125:135 */     this.vehiculo.setTipoVehiculo(new TipoVehiculo());
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Vehiculo getVehiculo()
/* 129:    */   {
/* 130:139 */     if (this.vehiculo == null) {
/* 131:140 */       crearVehiculo();
/* 132:    */     }
/* 133:142 */     return this.vehiculo;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setVehiculo(Vehiculo vehiculo)
/* 137:    */   {
/* 138:146 */     this.vehiculo = vehiculo;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<Transportista> getListaTransportistaCombo()
/* 142:    */   {
/* 143:150 */     if (this.listaTransportistaCombo == null) {
/* 144:151 */       this.listaTransportistaCombo = this.servicioTransportista.obtenerListaCombo("codigo", true, null);
/* 145:    */     }
/* 146:153 */     return this.listaTransportistaCombo;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setListaTransportistaCombo(List<Transportista> listaTransportistaCombo)
/* 150:    */   {
/* 151:157 */     this.listaTransportistaCombo = listaTransportistaCombo;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List<TipoVehiculo> getListaTipoVehiculoCombo()
/* 155:    */   {
/* 156:161 */     if (this.listaTipoVehiculoCombo == null) {
/* 157:162 */       this.listaTipoVehiculoCombo = this.servicioTipoVehiculo.obtenerListaCombo("codigo", true, null);
/* 158:    */     }
/* 159:164 */     return this.listaTipoVehiculoCombo;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setListaTipoVehiculoCombo(List<TipoVehiculo> listaTipoVehiculoCombo)
/* 163:    */   {
/* 164:168 */     this.listaTipoVehiculoCombo = listaTipoVehiculoCombo;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public LazyDataModel<Vehiculo> getListaVehiculo()
/* 168:    */   {
/* 169:172 */     return this.listaVehiculo;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setListaVehiculo(LazyDataModel<Vehiculo> listaVehiculo)
/* 173:    */   {
/* 174:176 */     this.listaVehiculo = listaVehiculo;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public List<Vehiculo> getListaVehiculoCombo()
/* 178:    */   {
/* 179:180 */     return this.listaVehiculoCombo;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setListaVehiculoCombo(List<Vehiculo> listaVehiculoCombo)
/* 183:    */   {
/* 184:184 */     this.listaVehiculoCombo = listaVehiculoCombo;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public DataTable getDtVehiculo()
/* 188:    */   {
/* 189:188 */     return this.dtVehiculo;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setDtVehiculo(DataTable dtVehiculo)
/* 193:    */   {
/* 194:192 */     this.dtVehiculo = dtVehiculo;
/* 195:    */   }
/* 196:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.VehiculoBean
 * JD-Core Version:    0.7.0.1
 */