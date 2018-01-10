/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.EstadoCivil;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEstadoCivil;
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
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class EstadoCivilBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioEstadoCivil servicioEstadoCivil;
/*  30:    */   private EstadoCivil estadoCivil;
/*  31:    */   private LazyDataModel<EstadoCivil> listaEstadoCivil;
/*  32:    */   private DataTable dtEstadoCivil;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 74 */     this.listaEstadoCivil = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<EstadoCivil> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 81 */         List<EstadoCivil> lista = new ArrayList();
/*  44: 82 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 84 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 85 */         lista = EstadoCivilBean.this.servicioEstadoCivil.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 87 */         EstadoCivilBean.this.listaEstadoCivil.setRowCount(EstadoCivilBean.this.servicioEstadoCivil.contarPorCriterio(filters));
/*  50:    */         
/*  51: 89 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearEstadoCivil()
/*  57:    */   {
/*  58:103 */     this.estadoCivil = new EstadoCivil();
/*  59:104 */     this.estadoCivil.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  60:105 */     this.estadoCivil.setIdSucursal(AppUtil.getSucursal().getId());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:114 */     if ((getEstadoCivil() != null) && (getEstadoCivil().getIdEstadoCivil() != 0)) {
/*  66:115 */       setEditado(true);
/*  67:    */     } else {
/*  68:117 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  69:    */     }
/*  70:119 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String guardar()
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77:129 */       this.servicioEstadoCivil.guardar(this.estadoCivil);
/*  78:130 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  79:131 */       setEditado(false);
/*  80:132 */       limpiar();
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:134 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  85:135 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:    */     }
/*  87:137 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String eliminar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:147 */       this.servicioEstadoCivil.eliminar(this.estadoCivil);
/*  95:148 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:150 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 100:151 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 101:    */     }
/* 102:153 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarDatos()
/* 106:    */   {
/* 107:162 */     return "";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:171 */     crearEstadoCivil();
/* 113:172 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public EstadoCivil getEstadoCivil()
/* 117:    */   {
/* 118:189 */     return this.estadoCivil;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setEstadoCivil(EstadoCivil estadoCivil)
/* 122:    */   {
/* 123:199 */     this.estadoCivil = estadoCivil;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public LazyDataModel<EstadoCivil> getListaEstadoCivil()
/* 127:    */   {
/* 128:208 */     return this.listaEstadoCivil;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setListaEstadoCivil(LazyDataModel<EstadoCivil> listaEstadoCivil)
/* 132:    */   {
/* 133:218 */     this.listaEstadoCivil = listaEstadoCivil;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DataTable getDtEstadoCivil()
/* 137:    */   {
/* 138:227 */     return this.dtEstadoCivil;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setDtEstadoCivil(DataTable dtEstadoCivil)
/* 142:    */   {
/* 143:237 */     this.dtEstadoCivil = dtEstadoCivil;
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.EstadoCivilBean
 * JD-Core Version:    0.7.0.1
 */