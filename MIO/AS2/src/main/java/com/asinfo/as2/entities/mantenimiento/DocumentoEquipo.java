/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="documento_equipo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_documento_equipo"})})
/*  20:    */ public class DocumentoEquipo
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="documento_equipo", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="documento_equipo")
/*  28:    */   @Column(name="id_documento_equipo")
/*  29:    */   private int idDocumentoEquipo;
/*  30:    */   @Column(name="id_organizacion")
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal")
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="nombre", nullable=false, length=100)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=2, max=100)
/*  37:    */   private String nombre;
/*  38:    */   @Column(name="descripcion", length=200, nullable=true)
/*  39:    */   @Size(max=200)
/*  40:    */   private String descripcion;
/*  41:    */   @Column(name="archivo", length=200, nullable=false)
/*  42:    */   @Size(max=200)
/*  43:    */   private String archivo;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_equipo", nullable=false)
/*  46:    */   private Equipo equipo;
/*  47:    */   
/*  48:    */   public int getId()
/*  49:    */   {
/*  50: 76 */     return this.idDocumentoEquipo;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getIdDocumentoEquipo()
/*  54:    */   {
/*  55: 83 */     return this.idDocumentoEquipo;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdDocumentoEquipo(int idDocumentoEquipo)
/*  59:    */   {
/*  60: 91 */     this.idDocumentoEquipo = idDocumentoEquipo;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdOrganizacion()
/*  64:    */   {
/*  65: 98 */     return this.idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdOrganizacion(int idOrganizacion)
/*  69:    */   {
/*  70:106 */     this.idOrganizacion = idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdSucursal()
/*  74:    */   {
/*  75:113 */     return this.idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdSucursal(int idSucursal)
/*  79:    */   {
/*  80:121 */     this.idSucursal = idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getNombre()
/*  84:    */   {
/*  85:128 */     return this.nombre;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setNombre(String nombre)
/*  89:    */   {
/*  90:136 */     this.nombre = nombre;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getDescripcion()
/*  94:    */   {
/*  95:143 */     return this.descripcion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setDescripcion(String descripcion)
/*  99:    */   {
/* 100:151 */     this.descripcion = descripcion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getArchivo()
/* 104:    */   {
/* 105:158 */     return this.archivo;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setArchivo(String archivo)
/* 109:    */   {
/* 110:166 */     this.archivo = archivo;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Equipo getEquipo()
/* 114:    */   {
/* 115:173 */     return this.equipo;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setEquipo(Equipo equipo)
/* 119:    */   {
/* 120:181 */     this.equipo = equipo;
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.DocumentoEquipo
 * JD-Core Version:    0.7.0.1
 */