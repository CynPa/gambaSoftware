/*   1:    */ package com.asinfo.as2.finaciero.SRI.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CodigoFormaPagoSRI;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.utils.DatosSRI;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class CodigoFormaPagoSRIBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private transient ServicioGenerico<CodigoFormaPagoSRI> servicioCodigoFormaPagoSRI;
/*  30:    */   private CodigoFormaPagoSRI codigoFormaPagoSRI;
/*  31:    */   private LazyDataModel<CodigoFormaPagoSRI> listaCodigoFormaPagoSRI;
/*  32:    */   private DataTable dtCodigoFormaPagoSRI;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 68 */     this.listaCodigoFormaPagoSRI = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 617376213460166729L;
/*  40:    */       
/*  41:    */       public List<CodigoFormaPagoSRI> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 75 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  44:    */         
/*  45: 77 */         filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/*  46: 78 */         List<CodigoFormaPagoSRI> lista = CodigoFormaPagoSRIBean.this.servicioCodigoFormaPagoSRI.obtenerListaPorPagina(CodigoFormaPagoSRI.class, startIndex, pageSize, sortField, ordenar, filters);
/*  47:    */         
/*  48:    */ 
/*  49: 81 */         CodigoFormaPagoSRIBean.this.listaCodigoFormaPagoSRI.setRowCount(CodigoFormaPagoSRIBean.this.servicioCodigoFormaPagoSRI.contarPorCriterio(CodigoFormaPagoSRI.class, filters));
/*  50: 82 */         return lista;
/*  51:    */       }
/*  52:    */     };
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String editar()
/*  56:    */   {
/*  57: 90 */     if ((getCodigoFormaPagoSRI() != null) && (getCodigoFormaPagoSRI().getId() != 0)) {
/*  58: 91 */       setEditado(true);
/*  59:    */     } else {
/*  60: 93 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  61:    */     }
/*  62: 95 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String guardar()
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:101 */       this.servicioCodigoFormaPagoSRI.guardar(getCodigoFormaPagoSRI());
/*  70:102 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  71:103 */       limpiar();
/*  72:104 */       setFormaPagoSRIEnMemoria();
/*  73:105 */       setEditado(false);
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77:107 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  78:108 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  79:    */     }
/*  80:110 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String eliminar()
/*  84:    */   {
/*  85:115 */     if ((getCodigoFormaPagoSRI() != null) && (getCodigoFormaPagoSRI().getId() != 0)) {
/*  86:    */       try
/*  87:    */       {
/*  88:117 */         this.servicioCodigoFormaPagoSRI.eliminar(getCodigoFormaPagoSRI());
/*  89:118 */         setFormaPagoSRIEnMemoria();
/*  90:119 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  91:    */       }
/*  92:    */       catch (Exception e)
/*  93:    */       {
/*  94:121 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  95:122 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  96:    */       }
/*  97:    */     } else {
/*  98:125 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  99:    */     }
/* 100:127 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String limpiar()
/* 104:    */   {
/* 105:132 */     this.codigoFormaPagoSRI = new CodigoFormaPagoSRI();
/* 106:133 */     this.codigoFormaPagoSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 107:134 */     this.codigoFormaPagoSRI.setActivo(true);
/* 108:135 */     this.codigoFormaPagoSRI.setPredeterminado(false);
/* 109:136 */     return null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String cargarDatos()
/* 113:    */   {
/* 114:141 */     return null;
/* 115:    */   }
/* 116:    */   
/* 117:    */   private void setFormaPagoSRIEnMemoria()
/* 118:    */   {
/* 119:145 */     Map<String, String> filters = new HashMap();
/* 120:146 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 121:147 */     filters.put("activo", String.valueOf(true));
/* 122:148 */     List<CodigoFormaPagoSRI> listCodigoFormaPagoSRIDB = this.servicioCodigoFormaPagoSRI.obtenerListaCombo(CodigoFormaPagoSRI.class, "predeterminado", false, filters);
/* 123:    */     
/* 124:150 */     DatosSRI.cargarFormaPago(AppUtil.getOrganizacion().getId(), listCodigoFormaPagoSRIDB);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public CodigoFormaPagoSRI getCodigoFormaPagoSRI()
/* 128:    */   {
/* 129:154 */     return this.codigoFormaPagoSRI;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setCodigoFormaPagoSRI(CodigoFormaPagoSRI codigoFormaPagoSRI)
/* 133:    */   {
/* 134:158 */     this.codigoFormaPagoSRI = codigoFormaPagoSRI;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public LazyDataModel<CodigoFormaPagoSRI> getListaCodigoFormaPagoSRI()
/* 138:    */   {
/* 139:162 */     return this.listaCodigoFormaPagoSRI;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setListaCodigoFormaPagoSRI(LazyDataModel<CodigoFormaPagoSRI> listaCodigoFormaPagoSRI)
/* 143:    */   {
/* 144:166 */     this.listaCodigoFormaPagoSRI = listaCodigoFormaPagoSRI;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public DataTable getDtCodigoFormaPagoSRI()
/* 148:    */   {
/* 149:170 */     return this.dtCodigoFormaPagoSRI;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setDtCodigoFormaPagoSRI(DataTable dtCodigoFormaPagoSRI)
/* 153:    */   {
/* 154:174 */     this.dtCodigoFormaPagoSRI = dtCodigoFormaPagoSRI;
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.configuracion.CodigoFormaPagoSRIBean
 * JD-Core Version:    0.7.0.1
 */