/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCondicionPago;
/*   6:    */ import com.asinfo.as2.entities.CondicionPago;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import javax.faces.model.SelectItem;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ import org.primefaces.component.datatable.DataTable;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class CondicionPagoBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 6978148560356267256L;
/*  29:    */   @EJB
/*  30:    */   private transient ServicioCondicionPago servicioCondicionPago;
/*  31:    */   private CondicionPago condicionPago;
/*  32:    */   private LazyDataModel<CondicionPago> listaCondicionPago;
/*  33:    */   private List<SelectItem> condicioPagoItem;
/*  34:    */   private List<CondicionPago> listaCondicionPagoCombo;
/*  35:    */   private DataTable dataTableCondicionesPago;
/*  36:    */   
/*  37:    */   @PostConstruct
/*  38:    */   public void init()
/*  39:    */   {
/*  40: 57 */     this.listaCondicionPago = new LazyDataModel()
/*  41:    */     {
/*  42:    */       private static final long serialVersionUID = 1L;
/*  43:    */       
/*  44:    */       public List<CondicionPago> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  45:    */       {
/*  46: 64 */         List<CondicionPago> lista = new ArrayList();
/*  47: 65 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  48:    */         
/*  49: 67 */         lista = CondicionPagoBean.this.servicioCondicionPago.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  50: 68 */         CondicionPagoBean.this.listaCondicionPago.setRowCount(CondicionPagoBean.this.servicioCondicionPago.contarPorCriterio(filters));
/*  51:    */         
/*  52: 70 */         return lista;
/*  53:    */       }
/*  54:    */     };
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String editar()
/*  58:    */   {
/*  59: 83 */     LOG.info("Ingreso en editar");
/*  60: 84 */     if (getCondicionPago().getId() > 0) {
/*  61: 85 */       setEditado(true);
/*  62:    */     } else {
/*  63: 87 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  64:    */     }
/*  65: 89 */     return "";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String guardar()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 99 */       this.servicioCondicionPago.guardar(this.condicionPago);
/*  73:100 */       limpiar();
/*  74:101 */       setEditado(false);
/*  75:102 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  76:    */     }
/*  77:    */     catch (Exception e)
/*  78:    */     {
/*  79:104 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  80:105 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  81:    */     }
/*  82:107 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String eliminar()
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89:117 */       this.servicioCondicionPago.eliminar(this.condicionPago);
/*  90:118 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:120 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  95:121 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  96:    */     }
/*  97:123 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String cargarDatos()
/* 101:    */   {
/* 102:132 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void crearCondicionPago()
/* 106:    */   {
/* 107:141 */     this.condicionPago = new CondicionPago();
/* 108:142 */     this.condicionPago.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 109:143 */     this.condicionPago.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String limpiar()
/* 113:    */   {
/* 114:150 */     crearCondicionPago();
/* 115:151 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void cargarCondicionPagoItems()
/* 119:    */   {
/* 120:159 */     List<CondicionPago> condicionPagos = new ArrayList();
/* 121:160 */     condicionPagos = getServicioCondicionPagoBean().obtenerListaCombo("nombre", true, null);
/* 122:161 */     this.condicioPagoItem = new ArrayList();
/* 123:162 */     for (CondicionPago condicionPagoX : condicionPagos)
/* 124:    */     {
/* 125:163 */       int value = condicionPagoX.getIdCondicionPago();
/* 126:164 */       String label = condicionPagoX.getNombre();
/* 127:165 */       SelectItem opcion = new SelectItem(Integer.valueOf(value), label);
/* 128:166 */       this.condicioPagoItem.add(opcion);
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public ServicioCondicionPago getServicioCondicionPagoBean()
/* 133:    */   {
/* 134:172 */     return this.servicioCondicionPago;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setServicioCondicionPagoBean(ServicioCondicionPago servicioCondicionPagoBean)
/* 138:    */   {
/* 139:176 */     this.servicioCondicionPago = servicioCondicionPagoBean;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public CondicionPago getCondicionPago()
/* 143:    */   {
/* 144:180 */     if (this.condicionPago == null) {
/* 145:181 */       crearCondicionPago();
/* 146:    */     }
/* 147:184 */     return this.condicionPago;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setCondicionPago(CondicionPago condicionPago)
/* 151:    */   {
/* 152:188 */     this.condicionPago = condicionPago;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public DataTable getDataTableCondicionesPago()
/* 156:    */   {
/* 157:192 */     return this.dataTableCondicionesPago;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setDataTableCondicionesPago(DataTable dataTableCondicionesPago)
/* 161:    */   {
/* 162:196 */     this.dataTableCondicionesPago = dataTableCondicionesPago;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<SelectItem> getCondicioPagoItem()
/* 166:    */   {
/* 167:200 */     if (this.condicioPagoItem == null) {
/* 168:201 */       cargarCondicionPagoItems();
/* 169:    */     }
/* 170:205 */     return this.condicioPagoItem;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setCondicioPagoItem(List<SelectItem> condicioPagoItem)
/* 174:    */   {
/* 175:209 */     this.condicioPagoItem = condicioPagoItem;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public LazyDataModel<CondicionPago> getListaCondicionPago()
/* 179:    */   {
/* 180:218 */     return this.listaCondicionPago;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setListaCondicionPago(LazyDataModel<CondicionPago> listaCondicionPago)
/* 184:    */   {
/* 185:230 */     this.listaCondicionPago = listaCondicionPago;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public List<CondicionPago> getListaCondicionPagoCombo()
/* 189:    */   {
/* 190:239 */     if (this.listaCondicionPagoCombo == null) {
/* 191:240 */       this.listaCondicionPagoCombo = this.servicioCondicionPago.obtenerListaCombo(null, false, null);
/* 192:    */     }
/* 193:242 */     return this.listaCondicionPagoCombo;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setListaCondicionPagoCombo(List<CondicionPago> listaCondicionPagoCombo)
/* 197:    */   {
/* 198:252 */     this.listaCondicionPagoCombo = listaCondicionPagoCombo;
/* 199:    */   }
/* 200:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.CondicionPagoBean
 * JD-Core Version:    0.7.0.1
 */