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
/*  13:    */ @Table(name="titulo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  14:    */ public class Titulo
/*  15:    */   extends EntidadBase
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -3727103577714693228L;
/*  18:    */   @Column(name="id_organizacion", nullable=false)
/*  19:    */   private int idOrganizacion;
/*  20:    */   @Column(name="id_sucursal", nullable=false)
/*  21:    */   private int idSucursal;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="titulo", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="titulo")
/*  25:    */   @Column(name="id_titulo")
/*  26:    */   private int idTitulo;
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
/*  41:    */   public Titulo() {}
/*  42:    */   
/*  43:    */   public Titulo(int idTitulo, String codigo, String nombre)
/*  44:    */   {
/*  45: 93 */     this.idTitulo = idTitulo;
/*  46: 94 */     this.codigo = codigo;
/*  47: 95 */     this.nombre = nombre;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getIdOrganizacion()
/*  51:    */   {
/*  52:108 */     return this.idOrganizacion;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIdOrganizacion(int idOrganizacion)
/*  56:    */   {
/*  57:118 */     this.idOrganizacion = idOrganizacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdSucursal()
/*  61:    */   {
/*  62:127 */     return this.idSucursal;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdSucursal(int idSucursal)
/*  66:    */   {
/*  67:137 */     this.idSucursal = idSucursal;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdTitulo()
/*  71:    */   {
/*  72:146 */     return this.idTitulo;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdTitulo(int idTitulo)
/*  76:    */   {
/*  77:156 */     this.idTitulo = idTitulo;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getCodigo()
/*  81:    */   {
/*  82:165 */     return this.codigo;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setCodigo(String codigo)
/*  86:    */   {
/*  87:175 */     this.codigo = codigo;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getNombre()
/*  91:    */   {
/*  92:184 */     return this.nombre;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setNombre(String nombre)
/*  96:    */   {
/*  97:194 */     this.nombre = nombre;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getDescripcion()
/* 101:    */   {
/* 102:203 */     return this.descripcion;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setDescripcion(String descripcion)
/* 106:    */   {
/* 107:213 */     this.descripcion = descripcion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean isActivo()
/* 111:    */   {
/* 112:222 */     return this.activo;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setActivo(boolean activo)
/* 116:    */   {
/* 117:232 */     this.activo = activo;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isPredeterminado()
/* 121:    */   {
/* 122:241 */     return this.predeterminado;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setPredeterminado(boolean predeterminado)
/* 126:    */   {
/* 127:251 */     this.predeterminado = predeterminado;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getId()
/* 131:    */   {
/* 132:261 */     return this.idTitulo;
/* 133:    */   }
/* 134:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Titulo
 * JD-Core Version:    0.7.0.1
 */