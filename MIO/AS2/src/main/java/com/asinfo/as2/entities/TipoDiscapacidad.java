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
/*  13:    */ @Table(name="tipo_discapacidad", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  14:    */ public class TipoDiscapacidad
/*  15:    */   extends EntidadBase
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 2242499886569721050L;
/*  18:    */   @Column(name="id_organizacion", nullable=false)
/*  19:    */   private int idOrganizacion;
/*  20:    */   @Column(name="id_sucursal", nullable=false)
/*  21:    */   private int idSucursal;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="tipo_discapacidad", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_discapacidad")
/*  25:    */   @Column(name="id_tipo_discapacidad")
/*  26:    */   private int idTipoDiscapacidad;
/*  27:    */   @Column(name="codigo", nullable=false, length=10)
/*  28:    */   @Size(min=2, max=10)
/*  29:    */   private String codigo;
/*  30:    */   @Column(name="nombre", nullable=false, length=50)
/*  31:    */   @Size(min=2, max=50)
/*  32:    */   private String nombre;
/*  33:    */   @Column(name="descripcion", nullable=true, length=200)
/*  34:    */   @Size(max=200)
/*  35:    */   private String descripcion;
/*  36:    */   @Column(name="activo", nullable=false)
/*  37:    */   private boolean activo;
/*  38:    */   @Column(name="predeterminado", nullable=false)
/*  39:    */   private boolean predeterminado;
/*  40:    */   
/*  41:    */   public TipoDiscapacidad() {}
/*  42:    */   
/*  43:    */   public TipoDiscapacidad(int idTipoDiscapacidad, String codigo, String nombre)
/*  44:    */   {
/*  45: 90 */     this.idTipoDiscapacidad = idTipoDiscapacidad;
/*  46: 91 */     this.nombre = nombre;
/*  47: 92 */     this.codigo = codigo;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getId()
/*  51:    */   {
/*  52:106 */     return this.idTipoDiscapacidad;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdOrganizacion()
/*  56:    */   {
/*  57:115 */     return this.idOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdOrganizacion(int idOrganizacion)
/*  61:    */   {
/*  62:125 */     this.idOrganizacion = idOrganizacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdSucursal()
/*  66:    */   {
/*  67:134 */     return this.idSucursal;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdSucursal(int idSucursal)
/*  71:    */   {
/*  72:144 */     this.idSucursal = idSucursal;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdTipoDiscapacidad()
/*  76:    */   {
/*  77:153 */     return this.idTipoDiscapacidad;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdTipoDiscapacidad(int idTipoDiscapacidad)
/*  81:    */   {
/*  82:163 */     this.idTipoDiscapacidad = idTipoDiscapacidad;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getNombre()
/*  86:    */   {
/*  87:172 */     return this.nombre;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setNombre(String nombre)
/*  91:    */   {
/*  92:182 */     this.nombre = nombre;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getCodigo()
/*  96:    */   {
/*  97:191 */     return this.codigo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setCodigo(String codigo)
/* 101:    */   {
/* 102:201 */     this.codigo = codigo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getDescripcion()
/* 106:    */   {
/* 107:210 */     return this.descripcion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setDescripcion(String descripcion)
/* 111:    */   {
/* 112:220 */     this.descripcion = descripcion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isActivo()
/* 116:    */   {
/* 117:229 */     return this.activo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setActivo(boolean activo)
/* 121:    */   {
/* 122:239 */     this.activo = activo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isPredeterminado()
/* 126:    */   {
/* 127:248 */     return this.predeterminado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setPredeterminado(boolean predeterminado)
/* 131:    */   {
/* 132:258 */     this.predeterminado = predeterminado;
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.TipoDiscapacidad
 * JD-Core Version:    0.7.0.1
 */