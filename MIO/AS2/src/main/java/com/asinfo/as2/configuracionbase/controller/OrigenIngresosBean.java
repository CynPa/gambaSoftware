/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrigenIngresos;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.OrigenIngresos;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.event.SelectEvent;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class OrigenIngresosBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -3344766710473334395L;
/*  29:    */   @EJB
/*  30:    */   private ServicioOrigenIngresos servicioOrigenIngresos;
/*  31:    */   private OrigenIngresos origenIngresos;
/*  32:    */   private LazyDataModel<OrigenIngresos> listaOrigenIngresos;
/*  33:    */   private List<OrigenIngresos> listaOrigenIngresosCombo;
/*  34:    */   private DataTable dtOrigenIngresos;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 60 */     this.listaOrigenIngresos = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = 1L;
/*  42:    */       
/*  43:    */       public List<OrigenIngresos> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 67 */         List<OrigenIngresos> lista = new ArrayList();
/*  46: 68 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47:    */         
/*  48: 70 */         lista = OrigenIngresosBean.this.servicioOrigenIngresos.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  49: 71 */         OrigenIngresosBean.this.listaOrigenIngresos.setRowCount(OrigenIngresosBean.this.servicioOrigenIngresos.contarPorCriterio(filters));
/*  50:    */         
/*  51: 73 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String editar()
/*  57:    */   {
/*  58: 86 */     if (getOrigenIngresos().getId() > 0) {
/*  59: 87 */       setEditado(true);
/*  60:    */     } else {
/*  61: 89 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  62:    */     }
/*  63: 92 */     return "";
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String guardar()
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70:104 */       this.servicioOrigenIngresos.guardar(getOrigenIngresos());
/*  71:105 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  72:106 */       limpiar();
/*  73:107 */       setEditado(false);
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77:110 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  78:111 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  79:    */     }
/*  80:114 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String eliminar()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:125 */       this.servicioOrigenIngresos.eliminar(getOrigenIngresos());
/*  88:126 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  89:    */     }
/*  90:    */     catch (Exception e)
/*  91:    */     {
/*  92:129 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  93:130 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  94:    */     }
/*  95:132 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String limpiar()
/*  99:    */   {
/* 100:142 */     crearOrigenIngresos();
/* 101:143 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String cargarDatos()
/* 105:    */   {
/* 106:153 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void crearOrigenIngresos()
/* 110:    */   {
/* 111:162 */     this.origenIngresos = new OrigenIngresos();
/* 112:163 */     this.origenIngresos.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 113:164 */     this.origenIngresos.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public List<OrigenIngresos> obtenerListaCombo()
/* 117:    */   {
/* 118:168 */     List<OrigenIngresos> lista = new ArrayList();
/* 119:169 */     String sortField = "nombre";
/* 120:170 */     lista = this.servicioOrigenIngresos.obtenerListaCombo(sortField, true, null);
/* 121:171 */     return lista;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void onRowSelect(SelectEvent event)
/* 125:    */   {
/* 126:180 */     this.origenIngresos = ((OrigenIngresos)event.getObject());
/* 127:    */   }
/* 128:    */   
/* 129:    */   public OrigenIngresos getOrigenIngresos()
/* 130:    */   {
/* 131:189 */     if (this.origenIngresos == null) {
/* 132:190 */       crearOrigenIngresos();
/* 133:    */     }
/* 134:192 */     return this.origenIngresos;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setOrigenIngresos(OrigenIngresos origenIngresos)
/* 138:    */   {
/* 139:202 */     this.origenIngresos = origenIngresos;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public LazyDataModel<OrigenIngresos> getListaOrigenIngresos()
/* 143:    */   {
/* 144:211 */     return this.listaOrigenIngresos;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setListaOrigenIngresos(LazyDataModel<OrigenIngresos> listaOrigenIngresos)
/* 148:    */   {
/* 149:221 */     this.listaOrigenIngresos = listaOrigenIngresos;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public DataTable getDtOrigenIngresos()
/* 153:    */   {
/* 154:230 */     return this.dtOrigenIngresos;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setDtOrigenIngresos(DataTable dtOrigenIngresos)
/* 158:    */   {
/* 159:240 */     this.dtOrigenIngresos = dtOrigenIngresos;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<OrigenIngresos> getListaOrigenIngresosCombo()
/* 163:    */   {
/* 164:244 */     if (this.listaOrigenIngresosCombo == null) {
/* 165:245 */       this.listaOrigenIngresosCombo = obtenerListaCombo();
/* 166:    */     }
/* 167:247 */     return this.listaOrigenIngresosCombo;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaOrigenIngresosCombo(List<OrigenIngresos> listaOrigenIngresosCombo)
/* 171:    */   {
/* 172:251 */     this.listaOrigenIngresosCombo = listaOrigenIngresosCombo;
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.OrigenIngresosBean
 * JD-Core Version:    0.7.0.1
 */