/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Chofer;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.Transportista;
/*   9:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioChofer;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.JsfUtil;
/*  14:    */ import com.asinfo.excepciones.ExcepcionAS2Identification;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ import org.primefaces.component.datatable.DataTable;
/*  24:    */ import org.primefaces.model.LazyDataModel;
/*  25:    */ import org.primefaces.model.SortOrder;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class ChoferBean
/*  30:    */   extends PageControllerAS2
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -8204372454439685260L;
/*  33:    */   @EJB
/*  34:    */   private ServicioChofer servicioChofer;
/*  35:    */   @EJB
/*  36:    */   private ServicioTransportista servicioTransportista;
/*  37:    */   private Chofer chofer;
/*  38:    */   private LazyDataModel<Chofer> listaChofer;
/*  39:    */   private List<Transportista> listaTransportistaCombo;
/*  40:    */   private DataTable dtChofer;
/*  41:    */   
/*  42:    */   @PostConstruct
/*  43:    */   public void init()
/*  44:    */   {
/*  45: 59 */     this.listaChofer = new LazyDataModel()
/*  46:    */     {
/*  47:    */       private static final long serialVersionUID = 1L;
/*  48:    */       
/*  49:    */       public List<Chofer> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  50:    */       {
/*  51: 66 */         List<Chofer> lista = new ArrayList();
/*  52: 67 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  53:    */         
/*  54: 69 */         ChoferBean.this.agregarFiltroOrganizacion(filters);
/*  55:    */         
/*  56: 71 */         lista = ChoferBean.this.servicioChofer.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  57: 72 */         ChoferBean.this.listaChofer.setRowCount(ChoferBean.this.servicioChofer.contarPorCriterio(filters));
/*  58:    */         
/*  59: 74 */         return lista;
/*  60:    */       }
/*  61:    */     };
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String editar()
/*  65:    */   {
/*  66: 82 */     if ((this.chofer != null) && (this.chofer.getId() != 0)) {
/*  67: 83 */       setEditado(true);
/*  68:    */     } else {
/*  69: 85 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  70:    */     }
/*  71: 87 */     return "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String guardar()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78: 93 */       this.servicioChofer.guardar(getChofer());
/*  79: 94 */       limpiar();
/*  80: 95 */       setEditado(false);
/*  81: 96 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  82:    */     }
/*  83:    */     catch (ExcepcionAS2Identification e)
/*  84:    */     {
/*  85: 98 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  86: 99 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  87:    */     }
/*  88:    */     catch (AS2Exception e)
/*  89:    */     {
/*  90:101 */       JsfUtil.addErrorMessage(e, "");
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:103 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  95:104 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  96:    */     }
/*  97:106 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String eliminar()
/* 101:    */   {
/* 102:111 */     if ((this.chofer != null) && (this.chofer.getId() != 0)) {
/* 103:    */       try
/* 104:    */       {
/* 105:113 */         this.servicioChofer.eliminar(this.chofer);
/* 106:114 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 107:115 */         limpiar();
/* 108:    */       }
/* 109:    */       catch (Exception e)
/* 110:    */       {
/* 111:117 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 112:118 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 113:    */       }
/* 114:    */     } else {
/* 115:121 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 116:    */     }
/* 117:123 */     return "";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String limpiar()
/* 121:    */   {
/* 122:128 */     crearChofer();
/* 123:129 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String cargarDatos()
/* 127:    */   {
/* 128:134 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void crearChofer()
/* 132:    */   {
/* 133:138 */     this.chofer = new Chofer();
/* 134:139 */     this.chofer.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 135:140 */     this.chofer.setIdSucursal(AppUtil.getSucursal().getId());
/* 136:141 */     this.chofer.setActivo(true);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Chofer getChofer()
/* 140:    */   {
/* 141:145 */     return this.chofer;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setChofer(Chofer chofer)
/* 145:    */   {
/* 146:149 */     this.chofer = chofer;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public List<Transportista> getListaTransportistaCombo()
/* 150:    */   {
/* 151:153 */     if (this.listaTransportistaCombo == null) {
/* 152:154 */       this.listaTransportistaCombo = this.servicioTransportista.obtenerListaCombo("codigo", true, agregarFiltroOrganizacion(null));
/* 153:    */     }
/* 154:156 */     return this.listaTransportistaCombo;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setListaTransportistaCombo(List<Transportista> listaTransportistaCombo)
/* 158:    */   {
/* 159:160 */     this.listaTransportistaCombo = listaTransportistaCombo;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public LazyDataModel<Chofer> getListaChofer()
/* 163:    */   {
/* 164:164 */     return this.listaChofer;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setListaChofer(LazyDataModel<Chofer> listaChofer)
/* 168:    */   {
/* 169:168 */     this.listaChofer = listaChofer;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public DataTable getDtChofer()
/* 173:    */   {
/* 174:172 */     return this.dtChofer;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setDtChofer(DataTable dtChofer)
/* 178:    */   {
/* 179:176 */     this.dtChofer = dtChofer;
/* 180:    */   }
/* 181:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.ChoferBean
 * JD-Core Version:    0.7.0.1
 */