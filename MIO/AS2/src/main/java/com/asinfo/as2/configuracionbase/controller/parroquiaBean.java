/*   1:    */ package com.asinfo.as2.configuracionbase.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCiudad;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioParroquia;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   7:    */ import com.asinfo.as2.entities.Ciudad;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Parroquia;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.event.SelectEvent;
/*  22:    */ import org.primefaces.model.LazyDataModel;
/*  23:    */ import org.primefaces.model.SortOrder;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class parroquiaBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -894352093078369552L;
/*  31:    */   @EJB
/*  32:    */   private ServicioParroquia servicioParroquia;
/*  33:    */   @EJB
/*  34:    */   private ServicioCiudad servicioCiudad;
/*  35:    */   private Parroquia parroquia;
/*  36:    */   private LazyDataModel<Parroquia> listaParroquia;
/*  37:    */   private DataTable dtParroquia;
/*  38:    */   
/*  39:    */   @PostConstruct
/*  40:    */   public void init()
/*  41:    */   {
/*  42: 53 */     this.listaParroquia = new LazyDataModel()
/*  43:    */     {
/*  44:    */       private static final long serialVersionUID = 1L;
/*  45:    */       
/*  46:    */       public List<Parroquia> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  47:    */       {
/*  48: 60 */         List<Parroquia> lista = new ArrayList();
/*  49: 61 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  50:    */         
/*  51: 63 */         lista = parroquiaBean.this.servicioParroquia.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  52: 64 */         parroquiaBean.this.listaParroquia.setRowCount(parroquiaBean.this.servicioParroquia.contarPorCriterio(filters));
/*  53:    */         
/*  54: 66 */         return lista;
/*  55:    */       }
/*  56:    */     };
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String editar()
/*  60:    */   {
/*  61: 77 */     if (getParroquia().getId() != 0) {
/*  62: 78 */       setEditado(true);
/*  63:    */     } else {
/*  64: 80 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  65:    */     }
/*  66: 83 */     return "";
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String guardar()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73: 90 */       this.servicioParroquia.guardar(getParroquia());
/*  74: 91 */       limpiar();
/*  75: 92 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  76: 93 */       setEditado(false);
/*  77:    */     }
/*  78:    */     catch (Exception e)
/*  79:    */     {
/*  80: 96 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  81: 97 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  82:    */     }
/*  83: 99 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String eliminar()
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90:105 */       this.servicioParroquia.eliminar(getParroquia());
/*  91:106 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  96:110 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  97:    */     }
/*  98:112 */     return "";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String limpiar()
/* 102:    */   {
/* 103:117 */     crearParroquia();
/* 104:118 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void crearParroquia()
/* 108:    */   {
/* 109:122 */     this.parroquia = new Parroquia();
/* 110:123 */     this.parroquia.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 111:124 */     this.parroquia.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String cargarDatos()
/* 115:    */   {
/* 116:131 */     return null;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Parroquia getParroquia()
/* 120:    */   {
/* 121:135 */     return this.parroquia;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setParroquia(Parroquia parroquia)
/* 125:    */   {
/* 126:139 */     this.parroquia = parroquia;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public LazyDataModel<Parroquia> getListaParroquia()
/* 130:    */   {
/* 131:143 */     return this.listaParroquia;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setListaParroquia(LazyDataModel<Parroquia> listaParroquia)
/* 135:    */   {
/* 136:147 */     this.listaParroquia = listaParroquia;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public DataTable getDtParroquia()
/* 140:    */   {
/* 141:151 */     return this.dtParroquia;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setDtParroquia(DataTable dtParroquia)
/* 145:    */   {
/* 146:155 */     this.dtParroquia = dtParroquia;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public List<Ciudad> autocompletarCiudad(String consulta)
/* 150:    */   {
/* 151:159 */     return this.servicioCiudad.autocompletarCiudad(consulta);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void actualizarParroquia(SelectEvent event)
/* 155:    */   {
/* 156:163 */     Ciudad ciudad = (Ciudad)event.getObject();
/* 157:164 */     actualizarParroquia(ciudad);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void actualizarParroquia(Ciudad ciudad)
/* 161:    */   {
/* 162:168 */     getParroquia().setCiudad(ciudad);
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.controller.parroquiaBean
 * JD-Core Version:    0.7.0.1
 */