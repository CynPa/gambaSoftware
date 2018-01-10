/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.NotNull;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="proceso_importacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  16:    */ public class ProcesoImportacion
/*  17:    */   extends EntidadBase
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="proceso_importacion", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="proceso_importacion")
/*  24:    */   @Column(name="id_proceso_importacion", unique=true, nullable=false)
/*  25:    */   private int idProcesoImportacion;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="codigo", nullable=false, length=10)
/*  31:    */   @NotNull
/*  32:    */   @Size(min=2, max=10)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=50)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=2, max=50)
/*  37:    */   private String nombre;
/*  38:    */   @Column(name="descripcion", nullable=true, length=200)
/*  39:    */   @Size(max=200)
/*  40:    */   private String descripcion;
/*  41:    */   @Column(name="activo", nullable=false)
/*  42:    */   private boolean activo;
/*  43:    */   @Column(name="predeterminado", nullable=false)
/*  44:    */   private boolean predeterminado;
/*  45:    */   
/*  46:    */   public ProcesoImportacion() {}
/*  47:    */   
/*  48:    */   public ProcesoImportacion(int idProcesoImportacion, String codigo, String nombre)
/*  49:    */   {
/*  50: 71 */     this.idProcesoImportacion = idProcesoImportacion;
/*  51: 72 */     this.codigo = codigo;
/*  52: 73 */     this.nombre = nombre;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getId()
/*  56:    */   {
/*  57: 78 */     return this.idProcesoImportacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdProcesoImportacion()
/*  61:    */   {
/*  62: 87 */     return this.idProcesoImportacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdProcesoImportacion(int idProcesoImportacion)
/*  66:    */   {
/*  67: 97 */     this.idProcesoImportacion = idProcesoImportacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdOrganizacion()
/*  71:    */   {
/*  72:106 */     return this.idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdOrganizacion(int idOrganizacion)
/*  76:    */   {
/*  77:116 */     this.idOrganizacion = idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdSucursal()
/*  81:    */   {
/*  82:125 */     return this.idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdSucursal(int idSucursal)
/*  86:    */   {
/*  87:135 */     this.idSucursal = idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getCodigo()
/*  91:    */   {
/*  92:144 */     return this.codigo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setCodigo(String codigo)
/*  96:    */   {
/*  97:154 */     this.codigo = codigo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getNombre()
/* 101:    */   {
/* 102:163 */     return this.nombre;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setNombre(String nombre)
/* 106:    */   {
/* 107:173 */     this.nombre = nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getDescripcion()
/* 111:    */   {
/* 112:182 */     return this.descripcion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setDescripcion(String descripcion)
/* 116:    */   {
/* 117:192 */     this.descripcion = descripcion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isActivo()
/* 121:    */   {
/* 122:201 */     return this.activo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setActivo(boolean activo)
/* 126:    */   {
/* 127:211 */     this.activo = activo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isPredeterminado()
/* 131:    */   {
/* 132:220 */     return this.predeterminado;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setPredeterminado(boolean predeterminado)
/* 136:    */   {
/* 137:230 */     this.predeterminado = predeterminado;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String toString()
/* 141:    */   {
/* 142:235 */     return this.nombre;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ProcesoImportacion
 * JD-Core Version:    0.7.0.1
 */