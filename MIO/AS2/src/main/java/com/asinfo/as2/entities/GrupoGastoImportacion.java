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
/*  13:    */ @Table(name="grupo_gasto_importacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  14:    */ public class GrupoGastoImportacion
/*  15:    */   extends EntidadBase
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -1842832859997163245L;
/*  18:    */   @Id
/*  19:    */   @TableGenerator(name="grupo_gasto_importacion", initialValue=0, allocationSize=50)
/*  20:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="grupo_gasto_importacion")
/*  21:    */   @Column(name="id_grupo_gasto_importacion")
/*  22:    */   private int idGrupoGastoImportacion;
/*  23:    */   @Column(name="id_organizacion", nullable=false)
/*  24:    */   private int idOrganizacion;
/*  25:    */   @Column(name="id_sucursal", nullable=false)
/*  26:    */   private int idSucursal;
/*  27:    */   @Column(name="codigo", nullable=false, length=10)
/*  28:    */   @Size(min=1)
/*  29:    */   private String codigo;
/*  30:    */   @Column(name="nombre", nullable=false, length=50)
/*  31:    */   @Size(min=3)
/*  32:    */   private String nombre;
/*  33:    */   @Column(name="descripcion", nullable=true, length=200)
/*  34:    */   private String descripcion;
/*  35:    */   @Column(name="activo", nullable=false)
/*  36:    */   private boolean activo;
/*  37:    */   @Column(name="predeterminado", nullable=false)
/*  38:    */   private boolean predeterminado;
/*  39:    */   
/*  40:    */   public GrupoGastoImportacion() {}
/*  41:    */   
/*  42:    */   public int getId()
/*  43:    */   {
/*  44: 77 */     return this.idGrupoGastoImportacion;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public GrupoGastoImportacion(int idGrupoGastoImportacion, String codigo, String nombre)
/*  48:    */   {
/*  49: 88 */     this.idGrupoGastoImportacion = idGrupoGastoImportacion;
/*  50: 89 */     this.codigo = codigo;
/*  51: 90 */     this.nombre = nombre;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getIdOrganizacion()
/*  55:    */   {
/*  56:103 */     return this.idOrganizacion;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getIdGrupoGastoImportacion()
/*  60:    */   {
/*  61:112 */     return this.idGrupoGastoImportacion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setIdGrupoGastoImportacion(int idGrupoGastoImportacion)
/*  65:    */   {
/*  66:120 */     this.idGrupoGastoImportacion = idGrupoGastoImportacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdOrganizacion(int idOrganizacion)
/*  70:    */   {
/*  71:128 */     this.idOrganizacion = idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdSucursal()
/*  75:    */   {
/*  76:137 */     return this.idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdSucursal(int idSucursal)
/*  80:    */   {
/*  81:146 */     this.idSucursal = idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getCodigo()
/*  85:    */   {
/*  86:155 */     return this.codigo;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setCodigo(String codigo)
/*  90:    */   {
/*  91:164 */     this.codigo = codigo;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getNombre()
/*  95:    */   {
/*  96:173 */     return this.nombre;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setNombre(String nombre)
/* 100:    */   {
/* 101:182 */     this.nombre = nombre;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getDescripcion()
/* 105:    */   {
/* 106:191 */     return this.descripcion;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setDescripcion(String descripcion)
/* 110:    */   {
/* 111:200 */     this.descripcion = descripcion;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean isActivo()
/* 115:    */   {
/* 116:209 */     return this.activo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setActivo(boolean activo)
/* 120:    */   {
/* 121:218 */     this.activo = activo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isPredeterminado()
/* 125:    */   {
/* 126:227 */     return this.predeterminado;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setPredeterminado(boolean predeterminado)
/* 130:    */   {
/* 131:236 */     this.predeterminado = predeterminado;
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.GrupoGastoImportacion
 * JD-Core Version:    0.7.0.1
 */