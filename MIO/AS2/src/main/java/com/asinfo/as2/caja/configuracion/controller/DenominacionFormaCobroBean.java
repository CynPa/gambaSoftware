/*   1:    */ package com.asinfo.as2.caja.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   6:    */ import com.asinfo.as2.entities.DenominacionFormaCobro;
/*   7:    */ import com.asinfo.as2.entities.FormaPago;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class DenominacionFormaCobroBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioGenerico<DenominacionFormaCobro> servicioDenominacionFormaCobro;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioFormaPago servicioFormaPago;
/*  35:    */   private DenominacionFormaCobro denominacionFormaCobro;
/*  36:    */   private LazyDataModel<DenominacionFormaCobro> listaDenominacionFormaCobro;
/*  37:    */   private List<FormaPago> listFormaPago;
/*  38:    */   private DataTable dtDenominacion;
/*  39:    */   
/*  40:    */   @PostConstruct
/*  41:    */   public void init()
/*  42:    */   {
/*  43: 64 */     this.listaDenominacionFormaCobro = new LazyDataModel()
/*  44:    */     {
/*  45:    */       private static final long serialVersionUID = 1L;
/*  46:    */       
/*  47:    */       public List<DenominacionFormaCobro> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  48:    */       {
/*  49: 72 */         List<DenominacionFormaCobro> lista = new ArrayList();
/*  50: 73 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  51: 74 */         List<String> listaCampos = new ArrayList();
/*  52: 75 */         listaCampos.add("formaPago");
/*  53: 76 */         lista = DenominacionFormaCobroBean.this.servicioDenominacionFormaCobro.obtenerListaPorPagina(DenominacionFormaCobro.class, startIndex, pageSize, sortField, ordenar, filters, listaCampos);
/*  54:    */         
/*  55:    */ 
/*  56: 79 */         DenominacionFormaCobroBean.this.listaDenominacionFormaCobro.setRowCount(DenominacionFormaCobroBean.this.servicioDenominacionFormaCobro.contarPorCriterio(DenominacionFormaCobro.class, filters));
/*  57:    */         
/*  58: 81 */         return lista;
/*  59:    */       }
/*  60:    */     };
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65: 89 */     if ((getDenominacionFormaCobro() != null) && (getDenominacionFormaCobro().getId() != 0))
/*  66:    */     {
/*  67: 90 */       List<String> listaCampos = new ArrayList();
/*  68: 91 */       listaCampos.add("formaPago");
/*  69: 92 */       this.denominacionFormaCobro = ((DenominacionFormaCobro)this.servicioDenominacionFormaCobro.cargarDetalle(DenominacionFormaCobro.class, getDenominacionFormaCobro().getId(), listaCampos));
/*  70:    */       
/*  71: 94 */       setEditado(true);
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75: 96 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  76:    */     }
/*  77: 98 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String guardar()
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:104 */       this.servicioDenominacionFormaCobro.guardar(getDenominacionFormaCobro());
/*  85:105 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  86:106 */       limpiar();
/*  87:107 */       setEditado(false);
/*  88:    */     }
/*  89:    */     catch (AS2Exception e)
/*  90:    */     {
/*  91:109 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  92:110 */       e.printStackTrace();
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:112 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  97:113 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  98:    */     }
/*  99:115 */     return null;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String eliminar()
/* 103:    */   {
/* 104:120 */     if ((getDenominacionFormaCobro() != null) && (getDenominacionFormaCobro().getId() != 0)) {
/* 105:121 */       this.servicioDenominacionFormaCobro.eliminar(getDenominacionFormaCobro());
/* 106:    */     } else {
/* 107:123 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 108:    */     }
/* 109:125 */     return null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String limpiar()
/* 113:    */   {
/* 114:130 */     this.denominacionFormaCobro = new DenominacionFormaCobro();
/* 115:131 */     this.denominacionFormaCobro.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 116:132 */     this.denominacionFormaCobro.setIdSucursal(AppUtil.getSucursal().getId());
/* 117:133 */     this.denominacionFormaCobro.setActivo(true);
/* 118:134 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String cargarDatos()
/* 122:    */   {
/* 123:140 */     return null;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public DenominacionFormaCobro getDenominacionFormaCobro()
/* 127:    */   {
/* 128:144 */     return this.denominacionFormaCobro;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setDenominacionFormaCobro(DenominacionFormaCobro denominacionFormaCobro)
/* 132:    */   {
/* 133:148 */     this.denominacionFormaCobro = denominacionFormaCobro;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public LazyDataModel<DenominacionFormaCobro> getListaDenominacionFormaCobro()
/* 137:    */   {
/* 138:152 */     return this.listaDenominacionFormaCobro;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setListaDenominacionFormaCobro(LazyDataModel<DenominacionFormaCobro> listaDenominacionFormaCobro)
/* 142:    */   {
/* 143:156 */     this.listaDenominacionFormaCobro = listaDenominacionFormaCobro;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List<FormaPago> getListFormaPago()
/* 147:    */   {
/* 148:160 */     if (this.listFormaPago == null) {
/* 149:161 */       this.listFormaPago = this.servicioFormaPago.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 150:    */     }
/* 151:163 */     return this.listFormaPago;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setListFormaPago(List<FormaPago> listFormaPago)
/* 155:    */   {
/* 156:167 */     this.listFormaPago = listFormaPago;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public DataTable getDtDenominacion()
/* 160:    */   {
/* 161:171 */     return this.dtDenominacion;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setDtDenominacion(DataTable dtDenominacion)
/* 165:    */   {
/* 166:175 */     this.dtDenominacion = dtDenominacion;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.configuracion.controller.DenominacionFormaCobroBean
 * JD-Core Version:    0.7.0.1
 */