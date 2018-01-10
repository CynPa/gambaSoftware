/*   1:    */ package com.asinfo.as2.datosbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioMoneda;
/*   6:    */ import com.asinfo.as2.entities.Moneda;
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
/*  20:    */ import org.primefaces.event.SelectEvent;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class MonedaBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -9061218854698018535L;
/*  30:    */   @EJB
/*  31:    */   ServicioMoneda servicioMoneda;
/*  32:    */   private Moneda moneda;
/*  33:    */   private LazyDataModel<Moneda> listaMoneda;
/*  34:    */   private DataTable dataTableMoneda;
/*  35:    */   private List<SelectItem> monedaItems;
/*  36:    */   
/*  37:    */   @PostConstruct
/*  38:    */   public void init()
/*  39:    */   {
/*  40: 63 */     this.listaMoneda = new LazyDataModel()
/*  41:    */     {
/*  42:    */       private static final long serialVersionUID = 1L;
/*  43:    */       
/*  44:    */       public List<Moneda> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  45:    */       {
/*  46: 70 */         List<Moneda> lista = new ArrayList();
/*  47: 71 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  48:    */         
/*  49: 73 */         lista = MonedaBean.this.servicioMoneda.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  50: 74 */         MonedaBean.this.listaMoneda.setRowCount(MonedaBean.this.servicioMoneda.contarPorCriterio(filters));
/*  51:    */         
/*  52: 76 */         return lista;
/*  53:    */       }
/*  54:    */     };
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String editar()
/*  58:    */   {
/*  59: 91 */     if (this.moneda.getIdMoneda() > 0) {
/*  60: 92 */       setEditado(true);
/*  61:    */     } else {
/*  62: 94 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  63:    */     }
/*  64: 97 */     return "";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String guardar()
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:106 */       this.servicioMoneda.guardar(this.moneda);
/*  72:107 */       limpiar();
/*  73:108 */       setEditado(false);
/*  74:109 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  75:    */     }
/*  76:    */     catch (Exception e)
/*  77:    */     {
/*  78:111 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  79:112 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  80:    */     }
/*  81:115 */     return "";
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String eliminar()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:124 */       this.servicioMoneda.eliminar(this.moneda);
/*  89:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  90:    */     }
/*  91:    */     catch (Exception e)
/*  92:    */     {
/*  93:127 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  94:128 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  95:    */     }
/*  96:131 */     return "";
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String cargarDatos()
/* 100:    */   {
/* 101:139 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String cargarItems()
/* 105:    */   {
/* 106:146 */     if (this.monedaItems == null)
/* 107:    */     {
/* 108:147 */       this.monedaItems = new ArrayList();
/* 109:149 */       for (Moneda moneda : this.listaMoneda)
/* 110:    */       {
/* 111:150 */         SelectItem opcion = new SelectItem(Integer.valueOf(moneda.getIdMoneda()), moneda.getNombre());
/* 112:151 */         this.monedaItems.add(opcion);
/* 113:    */       }
/* 114:    */     }
/* 115:155 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String limpiar()
/* 119:    */   {
/* 120:160 */     crearMoneda();
/* 121:161 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void crearMoneda()
/* 125:    */   {
/* 126:168 */     this.moneda = new Moneda();
/* 127:169 */     this.moneda.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 128:170 */     this.moneda.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void onRowSelect(SelectEvent event)
/* 132:    */   {
/* 133:177 */     Moneda moneda = (Moneda)event.getObject();
/* 134:178 */     setMoneda(moneda);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public ServicioMoneda getServicioMonedaBean()
/* 138:    */   {
/* 139:183 */     return this.servicioMoneda;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setServicioMonedaBean(ServicioMoneda servicioMoneda)
/* 143:    */   {
/* 144:187 */     this.servicioMoneda = servicioMoneda;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Moneda getMoneda()
/* 148:    */   {
/* 149:191 */     if (this.moneda == null) {
/* 150:192 */       crearMoneda();
/* 151:    */     }
/* 152:195 */     return this.moneda;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setMoneda(Moneda moneda)
/* 156:    */   {
/* 157:199 */     this.moneda = moneda;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public LazyDataModel<Moneda> getMonedas()
/* 161:    */   {
/* 162:203 */     return this.listaMoneda;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setMonedas(LazyDataModel<Moneda> listaMoneda)
/* 166:    */   {
/* 167:207 */     this.listaMoneda = listaMoneda;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public DataTable getDataTableMoneda()
/* 171:    */   {
/* 172:211 */     return this.dataTableMoneda;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setDataTableMoneda(DataTable dataTableMoneda)
/* 176:    */   {
/* 177:215 */     this.dataTableMoneda = dataTableMoneda;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public List<SelectItem> getMonedaItems()
/* 181:    */   {
/* 182:219 */     cargarItems();
/* 183:    */     
/* 184:221 */     return this.monedaItems;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setMonedaItems(List<SelectItem> monedaItems)
/* 188:    */   {
/* 189:225 */     this.monedaItems = monedaItems;
/* 190:    */   }
/* 191:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.MonedaBean
 * JD-Core Version:    0.7.0.1
 */