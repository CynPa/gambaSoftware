/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.NivelInstruccion;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioNivelInstruccion;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import javax.annotation.PostConstruct;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import org.apache.log4j.Logger;
/*  17:    */ import org.primefaces.component.datatable.DataTable;
/*  18:    */ import org.primefaces.model.LazyDataModel;
/*  19:    */ import org.primefaces.model.SortOrder;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class NivelInstruccionBean
/*  24:    */   extends PageControllerAS2
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = -3573819948628279185L;
/*  27:    */   @EJB
/*  28:    */   private transient ServicioNivelInstruccion servicioNivelInstruccion;
/*  29:    */   private NivelInstruccion nivelInstruccion;
/*  30:    */   private LazyDataModel<NivelInstruccion> listaNivelInstruccion;
/*  31:    */   private DataTable dtNivelInstruccion;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 70 */     this.listaNivelInstruccion = new LazyDataModel()
/*  37:    */     {
/*  38:    */       private static final long serialVersionUID = 6332796485708443987L;
/*  39:    */       
/*  40:    */       public List<NivelInstruccion> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  41:    */       {
/*  42: 76 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  43:    */         
/*  44: 78 */         List<NivelInstruccion> lista = NivelInstruccionBean.this.servicioNivelInstruccion.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  45:    */         
/*  46: 80 */         NivelInstruccionBean.this.listaNivelInstruccion.setRowCount(NivelInstruccionBean.this.servicioNivelInstruccion.contarPorCriterio(filters));
/*  47: 81 */         return lista;
/*  48:    */       }
/*  49:    */     };
/*  50:    */   }
/*  51:    */   
/*  52:    */   private void crearEntidad()
/*  53:    */   {
/*  54: 94 */     this.nivelInstruccion = new NivelInstruccion();
/*  55: 95 */     this.nivelInstruccion.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  56: 96 */     this.nivelInstruccion.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String editar()
/*  60:    */   {
/*  61:105 */     if ((getNivelInstruccion() != null) && (getNivelInstruccion().getIdNivelInstruccion() != 0)) {
/*  62:106 */       setEditado(true);
/*  63:    */     } else {
/*  64:108 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  65:    */     }
/*  66:110 */     return "";
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String guardar()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:120 */       this.servicioNivelInstruccion.guardar(this.nivelInstruccion);
/*  74:121 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  75:122 */       setEditado(false);
/*  76:123 */       limpiar();
/*  77:    */     }
/*  78:    */     catch (Exception e)
/*  79:    */     {
/*  80:125 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  81:126 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  82:    */     }
/*  83:128 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String eliminar()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:139 */       this.servicioNivelInstruccion.eliminar(this.nivelInstruccion);
/*  91:140 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  96:143 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  97:    */     }
/*  98:145 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String cargarDatos()
/* 102:    */   {
/* 103:154 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String limpiar()
/* 107:    */   {
/* 108:163 */     crearEntidad();
/* 109:164 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public NivelInstruccion getNivelInstruccion()
/* 113:    */   {
/* 114:181 */     if (this.nivelInstruccion == null) {
/* 115:182 */       crearEntidad();
/* 116:    */     }
/* 117:184 */     return this.nivelInstruccion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setNivelInstruccion(NivelInstruccion nivelInstruccion)
/* 121:    */   {
/* 122:194 */     this.nivelInstruccion = nivelInstruccion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public LazyDataModel<NivelInstruccion> getListaNivelInstruccion()
/* 126:    */   {
/* 127:203 */     return this.listaNivelInstruccion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setListaNivelInstruccion(LazyDataModel<NivelInstruccion> listaNivelInstruccion)
/* 131:    */   {
/* 132:213 */     this.listaNivelInstruccion = listaNivelInstruccion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public DataTable getDtNivelInstruccion()
/* 136:    */   {
/* 137:221 */     return this.dtNivelInstruccion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setDtNivelInstruccion(DataTable dtNivelInstruccion)
/* 141:    */   {
/* 142:229 */     this.dtNivelInstruccion = dtNivelInstruccion;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.NivelInstruccionBean
 * JD-Core Version:    0.7.0.1
 */