/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ import javax.persistence.TableGenerator;
/*  10:    */ import javax.validation.constraints.Size;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="tipo_tramite_importacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  14:    */ public class TipoTramiteImportacion
/*  15:    */   extends EntidadBase
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -6020933067443475033L;
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="tipo_tramite_importacion", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_tramite_importacion")
/*  21:    */   @Column(name="id_tipo_tramite_importacion")
/*  22:    */   private int idTipoTramiteImportacion;
/*  23:    */   @Column(name="id_organizacion", nullable=false)
/*  24:    */   private int idOrganizacion;
/*  25:    */   @Column(name="id_sucursal", nullable=false)
/*  26:    */   private int idSucursal;
/*  27:    */   @Column(name="codigo", nullable=false)
/*  28:    */   @Size(min=1, max=10)
/*  29:    */   private String codigo;
/*  30:    */   @Column(name="nombre", nullable=false)
/*  31:    */   @Size(min=3, max=50)
/*  32:    */   private String nombre;
/*  33:    */   @Column(name="descripcion", length=200, nullable=true)
/*  34:    */   @Size(max=200)
/*  35:    */   private String descripcion;
/*  36:    */   @Column(name="activo", nullable=false)
/*  37:    */   private boolean activo;
/*  38:    */   @Column(name="predeterminado", nullable=false)
/*  39:    */   private boolean predeterminado;
/*  40:    */   
/*  41:    */   public TipoTramiteImportacion() {}
/*  42:    */   
/*  43:    */   public TipoTramiteImportacion(int idTipoTramiteImportacion, String codigo, String nombre)
/*  44:    */   {
/*  45: 80 */     this.idTipoTramiteImportacion = idTipoTramiteImportacion;
/*  46: 81 */     this.codigo = codigo;
/*  47: 82 */     this.nombre = nombre;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getId()
/*  51:    */   {
/*  52: 91 */     return this.idTipoTramiteImportacion;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdTipoTramiteImportacion()
/*  56:    */   {
/*  57:105 */     return this.idTipoTramiteImportacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdTipoTramiteImportacion(int idTipoTramiteImportacion)
/*  61:    */   {
/*  62:113 */     this.idTipoTramiteImportacion = idTipoTramiteImportacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdOrganizacion()
/*  66:    */   {
/*  67:121 */     return this.idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdOrganizacion(int idOrganizacion)
/*  71:    */   {
/*  72:129 */     this.idOrganizacion = idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdSucursal()
/*  76:    */   {
/*  77:137 */     return this.idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdSucursal(int idSucursal)
/*  81:    */   {
/*  82:145 */     this.idSucursal = idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getCodigo()
/*  86:    */   {
/*  87:153 */     return this.codigo;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setCodigo(String codigo)
/*  91:    */   {
/*  92:161 */     this.codigo = codigo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getNombre()
/*  96:    */   {
/*  97:169 */     return this.nombre;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setNombre(String nombre)
/* 101:    */   {
/* 102:177 */     this.nombre = nombre;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getDescripcion()
/* 106:    */   {
/* 107:185 */     return this.descripcion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setDescripcion(String descripcion)
/* 111:    */   {
/* 112:193 */     this.descripcion = descripcion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isActivo()
/* 116:    */   {
/* 117:201 */     return this.activo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setActivo(boolean activo)
/* 121:    */   {
/* 122:209 */     this.activo = activo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isPredeterminado()
/* 126:    */   {
/* 127:217 */     return this.predeterminado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setPredeterminado(boolean predeterminado)
/* 131:    */   {
/* 132:225 */     this.predeterminado = predeterminado;
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoTramiteImportacion
 * JD-Core Version:    0.7.0.1
 */